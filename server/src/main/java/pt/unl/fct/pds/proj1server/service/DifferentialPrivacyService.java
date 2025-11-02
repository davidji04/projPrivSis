package pt.unl.fct.pds.proj1server.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import pt.unl.fct.pds.proj1server.model.AverageRequest;
import pt.unl.fct.pds.proj1server.model.CountRequest;
import pt.unl.fct.pds.proj1server.model.HistogramRequest;
import pt.unl.fct.pds.proj1server.model.DPHistogramResponse;
import pt.unl.fct.pds.proj1server.model.DPResponse;

import pt.unl.fct.pds.proj1server.repository.MedDataRepository;

@Service
public class DifferentialPrivacyService {

  private final double CLIP_MIN = 0.0;
  private final double CLIP_MAX = 122.0;

  @Autowired
  private PrivacyBudgetService privacyBudgetService;

  @Autowired
  private MedDataRepository medDataRepository;

  @Value("${dp.privacy.epsilon_per_count:0.1}")
  private double epsilonPerCount;
  @Value("${dp.privacy.epsilon_per_average:0.2}")
  private double epsilonPerAverage;
  @Value("${dp.privacy.epsilon_per_histogram_bin:0.4}")
  private double epsilonPerHistogramBin;

  private final List<String> genders = List.of("Male", "Female", "Non-binary", "Polygender", "Genderqueer",
      "Genderfluid", "Agender", "Bigender");

  private final Random random = new Random();

  public double addLaplaceNoise(double trueValue, double sensitivity, double epsilon) {
    double scale = sensitivity / epsilon;
    double noise = generateLaplaceNoise(scale);
    return trueValue + noise;
  }

  private double generateLaplaceNoise(double scale) {
    double u = random.nextDouble() - 0.5;
    if (u == 0.0)
      return 0.0;
    return -scale * Math.signum(u) * Math.log(1 - 2 * Math.abs(u));
  }

  public DPResponse getNoisyCount(CountRequest request) throws PrivacyBudgetExceededException {

    final double sensitivity = 1.0;
    final double epsilonCost = this.epsilonPerCount;

    privacyBudgetService.consumeBudget(epsilonCost);

    long trueCount = medDataRepository.count();
    double noisedCount = this.addLaplaceNoise((double) trueCount, sensitivity, epsilonCost);

    DPResponse response = new DPResponse();
    response.setAttributesQueried("count(*)");
    response.setNoisedResponse(noisedCount);
    return response;
  }

  public DPResponse getNoisyAverage(AverageRequest request) throws PrivacyBudgetExceededException {
    final double sensitivityCount = 1.0;
    final double sensitivitySum = CLIP_MAX; // age is clipped between 0 and 122
    final double totalEpsilonCost = this.epsilonPerAverage;
    final double epsilonForCount = totalEpsilonCost;
    final double epsilonForSum = totalEpsilonCost;

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    long trueCount = medDataRepository.count();
    double trueSum = medDataRepository.getSumOfAgeWithClipping(CLIP_MIN, CLIP_MAX);
    double noisedCount = this.addLaplaceNoise((double) trueCount, sensitivityCount, epsilonForCount);
    double noisedSum = this.addLaplaceNoise(trueSum, sensitivitySum, epsilonForSum);
    double noisedAverage = noisedSum / noisedCount;

    DPResponse response = new DPResponse();
    response.setAttributesQueried("average(age)");
    response.setNoisedResponse(noisedAverage);
    return response;
  }

  public DPHistogramResponse getNoisyAvgByGender(HistogramRequest request) throws PrivacyBudgetExceededException {

    List<String> bins = this.genders;

    final double sensitivityCount = 1.0;
    final double sensitivitySum = CLIP_MAX;

    final double totalEpsilonCostPerBin = this.epsilonPerHistogramBin;
    final double epsilonForCount = totalEpsilonCostPerBin;
    final double epsilonForSum = totalEpsilonCostPerBin;

    final double totalEpsilonCost = totalEpsilonCostPerBin * bins.size();

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();

    for (String gender : bins) {
      long trueCount = medDataRepository.countByGender(gender);
      Double trueSumNullable = medDataRepository.getSumOfAgeWithClippingAndGender(CLIP_MIN, CLIP_MAX, gender);
      double trueSum = (trueSumNullable == null) ? 0.0 : trueSumNullable.doubleValue();

      double noisedCount = this.addLaplaceNoise(trueCount, sensitivityCount, epsilonForCount);
      double noisedSum = this.addLaplaceNoise(trueSum, sensitivitySum, epsilonForSum);

      double noisedAverage = noisedSum / noisedCount;
      noisedResults.put(gender, noisedAverage);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("average(age) by gender");
    response.setNoisedResponse(noisedResults);
    return response;
  }

  public DPHistogramResponse getNoisyCountByGender(HistogramRequest request) throws PrivacyBudgetExceededException {

    final double sensitivity = 1.0;
    final double epsilonPerBin = this.epsilonPerHistogramBin;
    final double totalEpsilonCost = epsilonPerBin * genders.size();

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();
    for (String gender : genders) {
      long trueCount = medDataRepository.countByGender(gender);
      double noisedCount = this.addLaplaceNoise(trueCount, sensitivity, epsilonPerBin);
      noisedResults.put(gender, noisedCount);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("count() by gender");
    response.setNoisedResponse(noisedResults);
    return response;
  }

  public DPHistogramResponse getNoisyAgeHistogram(HistogramRequest request) throws PrivacyBudgetExceededException {
    Map<String, int[]> bins = new LinkedHashMap<>();
    bins.put("0-17", new int[] { 0, 17 });
    bins.put("18-24", new int[] { 18, 24 });
    bins.put("25-39", new int[] { 25, 39 });
    bins.put("40-64", new int[] { 40, 64 });
    bins.put("65+", new int[] { 65, (int) CLIP_MAX });

    final double sensitivity = 1.0;
    final double epsilonPerBin = this.epsilonPerHistogramBin;
    final double totalEpsilonCost = epsilonPerBin * bins.size();

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();
    for (Map.Entry<String, int[]> entry : bins.entrySet()) {
      String binName = entry.getKey();
      int minAge = entry.getValue()[0];
      int maxAge = entry.getValue()[1];

      long trueCount = medDataRepository.countByAgeRange(minAge, maxAge);
      double noisedCount = this.addLaplaceNoise(trueCount, sensitivity, epsilonPerBin);
      noisedResults.put(binName, noisedCount);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("count() by age_range");
    response.setNoisedResponse(noisedResults);
    return response;
  }
}