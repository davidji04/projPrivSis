package pt.unl.fct.pds.proj1server.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

  @Autowired
  private PrivacyBudgetService privacyBudgetService;

  @Autowired
  private MedDataRepository medDataRepository;

  @Value("${dp.privacy.epsilon_per_count:0.1}")
  private double epsilonPerCount;

  @Value("${dp.privacy.epsilon_per_average:0.2}")
  private double epsilonPerAverage;

  @Value("${dp.privacy.epsilon_per_histogram_bin:0.05}")
  private double epsilonPerHistogramBin;

  @Value("${dp.privacy.epsilon_per_avg_histogram_bin:1.0}")
  private double epsilonPerAvgHistogramBin;

  @Value("${dp.privacy.epsilon_per_avg_by_gender_bin:10.0}")
  private double epsilonPerAvgByGenderBin;

  @Value("${dp.privacy.epsilon_per_count_by_gender_bin:10.0}")
  private double epsilonPerCountByGenderBin;
  @Value("${dp.privacy.epsilon_per_age_histogram_bin:10.0}")
  private double epsilonPerAgeHistogramBin;

  List<String> genders = List.of("Male", "Female", "Non-binary", "Polygender", "Genderqueer", "Genderfluid", "Agender",
      "Bigender");

  public double addLaplaceNoise(double trueValue, double sensitivity, double epsilon) {
    double scale = sensitivity / epsilon;
    double noise = generateLaplaceNoise(scale);
    return trueValue + noise;
  }

  private double generateLaplaceNoise(double scale) {
    double u = Math.random() - 0.5;
    return -scale * Math.signum(u) * Math.log(1 - 2 * Math.abs(u));
  }

  public DPResponse getNoisyCount(CountRequest request) {

    final double sensitivity = 1.0;

    final double epsilonCost = this.epsilonPerCount;

    privacyBudgetService.consumeBudget(epsilonCost);

    long trueCount = medDataRepository.count();

    double noisedCount = this.addLaplaceNoise(
        (double) trueCount,
        sensitivity,
        epsilonCost);

    double remainingBudget = privacyBudgetService.getRemainingBudget();

    DPResponse response = new DPResponse();
    response.setAttributesQueried("med_data:count(*)");
    response.setNoisedResponse(noisedCount);
    response.setQuerySensitivity(sensitivity);
    response.setUpdatedPrivacyBudget(remainingBudget);

    return response;
  }

  public DPResponse getNoisyAverage(AverageRequest request) {

    final double CLIP_MIN = 0.0;
    final double CLIP_MAX = 122.0;

    final double sensitivityCount = 1.0;
    final double sensitivitySum = CLIP_MAX;

    final double totalEpsilonCost = this.epsilonPerAverage;

    final double epsilonForCount = totalEpsilonCost / 2.0;
    final double epsilonForSum = totalEpsilonCost / 2.0;

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    long trueCount = medDataRepository.count();
    double trueSum = medDataRepository.getSumOfAgeWithClipping(CLIP_MIN, CLIP_MAX);

    double noisedCount = this.addLaplaceNoise((double) trueCount, sensitivityCount, epsilonForCount);
    double noisedSum = this.addLaplaceNoise(trueSum, sensitivitySum, epsilonForSum);

    double noisedAverage = 0.0;
    if (noisedCount > 0.5) {
      noisedAverage = noisedSum / noisedCount;
    }

    DPResponse response = new DPResponse();
    response.setAttributesQueried("med_data:average(age)");
    response.setNoisedResponse(noisedAverage);
    response.setQuerySensitivity(sensitivitySum);
    response.setUpdatedPrivacyBudget(privacyBudgetService.getRemainingBudget());

    return response;
  }

  public DPHistogramResponse getNoisyAvgByGender(HistogramRequest request) throws PrivacyBudgetExceededException {

    List<String> bins = List.of("Male", "Female", "Non-binary", "Polygender", "Genderqueer", "Genderfluid", "Agender",
        "Bigender");

    final double sensitivityCount = 1.0;
    final double sensitivitySum = 122;

    final double totalEpsilonCostPerBin = this.epsilonPerAvgByGenderBin;
    final double epsilonForCount = totalEpsilonCostPerBin / 2.0;
    final double epsilonForSum = totalEpsilonCostPerBin / 2.0;

    final double totalEpsilonCost = totalEpsilonCostPerBin * bins.size();

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();

    for (String gender : bins) {
      long trueCount = medDataRepository.countByGender(gender);
      Double trueSumNullable = medDataRepository.getSumOfAgeWithClippingAndGender(0, 122, gender);
      double trueSum = (trueSumNullable == null) ? 0.0 : trueSumNullable.doubleValue();

      double noisedCount = this.addLaplaceNoise(trueCount, sensitivityCount, epsilonForCount);
      double noisedSum = this.addLaplaceNoise(trueSum, sensitivitySum, epsilonForSum);

      double noisedAverage = 0.0;
      if (noisedCount > 0.5) {
        noisedAverage = noisedSum / noisedCount;
      }
      noisedResults.put(gender, noisedAverage);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("med_data:average(age) by gender");
    response.setNoisedResponse(noisedResults);
    response.setQuerySensitivity(sensitivitySum);
    response.setUpdatedPrivacyBudget(privacyBudgetService.getRemainingBudget());
    return response;
  }

  public DPHistogramResponse getNoisyCountByGender(HistogramRequest request) throws PrivacyBudgetExceededException {

    final double sensitivity = 1.0; // Esta é uma query COUNT, sensibilidade é 1
    final double epsilonPerBin = this.epsilonPerCountByGenderBin; // 10.0 (Alto!)
    final double totalEpsilonCost = epsilonPerBin * genders.size(); // 10.0 * 5 = 50.0

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();
    for (String gender : genders) {
      long trueCount = medDataRepository.countByGender(gender);

      // A escala do ruído é 1.0 / 10.0 = 0.1 (muito baixa)
      double noisedCount = this.addLaplaceNoise(trueCount, sensitivity, epsilonPerBin);

      noisedResults.put(gender, noisedCount);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("med_data:count() by gender");
    response.setNoisedResponse(noisedResults);
    response.setQuerySensitivity(sensitivity);
    response.setUpdatedPrivacyBudget(privacyBudgetService.getRemainingBudget());
    return response;
  }

  public DPHistogramResponse getNoisyAgeHistogram(HistogramRequest request) throws PrivacyBudgetExceededException {

    Map<String, int[]> genders = new LinkedHashMap<>();
    genders.put("0-17", new int[] { 0, 17 });
    genders.put("18-24", new int[] { 18, 24 });
    genders.put("25-39", new int[] { 25, 39 });
    genders.put("40-64", new int[] { 40, 64 });
    genders.put("65+", new int[] { 65, 122 });

    final double sensitivity = 1.0;
    final double epsilonPerBin = this.epsilonPerAgeHistogramBin;
    final double totalEpsilonCost = epsilonPerBin * genders.size();

    privacyBudgetService.consumeBudget(totalEpsilonCost);

    Map<String, Double> noisedResults = new HashMap<>();
    for (Map.Entry<String, int[]> entry : genders.entrySet()) {
      String binName = entry.getKey();
      int minAge = entry.getValue()[0];
      int maxAge = entry.getValue()[1];

      long trueCount = medDataRepository.countByAgeRange(minAge, maxAge);

      double noisedCount = this.addLaplaceNoise(trueCount, sensitivity, epsilonPerBin);

      noisedResults.put(binName, noisedCount);
    }

    DPHistogramResponse response = new DPHistogramResponse();
    response.setAttributesQueried("med_data:count() by age_range");
    response.setNoisedResponse(noisedResults);
    response.setQuerySensitivity(sensitivity);
    response.setUpdatedPrivacyBudget(privacyBudgetService.getRemainingBudget());
    return response;
  }

}
