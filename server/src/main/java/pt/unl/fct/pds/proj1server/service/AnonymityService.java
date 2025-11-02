package pt.unl.fct.pds.proj1server.service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import pt.unl.fct.pds.proj1server.model.MedDataKAnon;
import pt.unl.fct.pds.proj1server.model.ProcessRecord;
import pt.unl.fct.pds.proj1server.repository.MedDataDeidentifiedRepository;
import pt.unl.fct.pds.proj1server.repository.MedDataKAnonRepository;

@Service
public class AnonymityService {

    @Value("${kanonymity}")
    private int k;

    @Autowired
    private MedDataDeidentifiedRepository medDataDiRepo;

    @Autowired
    private MedDataKAnonRepository medDataKAnonRepo;

    public void runAnonymization() {
        List<ProcessRecord> allRecords = medDataDiRepo.findAll().stream().map(ProcessRecord::new).collect(Collectors.toList());

        List<List<ProcessRecord>> finalPartitions = partition(allRecords);

        List<MedDataKAnon> anonymizedResults = new ArrayList<>();

        for (List<ProcessRecord> partition : finalPartitions) {
            anonymizedResults.addAll(generalizeOrSuppress(partition));
        }
        medDataKAnonRepo.deleteAll();
        medDataKAnonRepo.saveAll(anonymizedResults);
        saveToCsv();
    }

    private void saveToCsv() {
        List<MedDataKAnon> records = medDataKAnonRepo.findAll();

        StringBuilder csvBuilder = new StringBuilder();

        csvBuilder.append("age,gender,postalCode,diagnosis\n");

        for (MedDataKAnon record : records) {
            csvBuilder.append(record.getAge()).append(",");
            csvBuilder.append(record.getGender()).append(",");
            csvBuilder.append(record.getPostalCode()).append(",");
            csvBuilder.append(record.getDiagnosis()).append("\n");
        }
        String fileName = "kanonMedData.csv";
        
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(csvBuilder.toString());
        } catch (IOException e) {
            System.err.println("Error saving CSV file: " + e.getMessage());
        }
    }

    private List<List<ProcessRecord>> partition(List<ProcessRecord> records) {

        if (records.size() < 2 * k) {
            return Collections.singletonList(records);
        }

        String bestDimension = findBestDimensionToSplit(records);

        records.sort(Comparator.comparing(r -> r.getValue(bestDimension)));

        int medianIndex = records.size() / 2;
        List<ProcessRecord> leftPartition = new ArrayList<>(records.subList(0, medianIndex));
        List<ProcessRecord> rightPartition = new ArrayList<>(records.subList(medianIndex, records.size()));

        List<List<ProcessRecord>> finalPartitions = new ArrayList<>();
        finalPartitions.addAll(partition(leftPartition));
        finalPartitions.addAll(partition(rightPartition));

        return finalPartitions;
    }

    private String findBestDimensionToSplit(List<ProcessRecord> records) {
        
        Set<Integer> uniqueAges = new HashSet<>();
        Set<String> uniqueGenders = new HashSet<>();
        Set<String> uniquePostals = new HashSet<>();

        for (ProcessRecord r : records) {
            uniqueAges.add(r.getAge());
            uniqueGenders.add(r.getGender());
            uniquePostals.add(r.getPostalCode());
        }
        int ageRange = uniqueAges.size();
        int genderRange = uniqueGenders.size();
        int postalRange = uniquePostals.size();

        if (ageRange >= genderRange && ageRange >= postalRange) {
            return "age";
        } else if (postalRange >= ageRange && postalRange >= genderRange) {
            return "postalCode";
        } else {
            return "gender";
        }
    }

    private List<MedDataKAnon> generalizeOrSuppress(List<ProcessRecord> partition) {
        List<MedDataKAnon> results = new ArrayList<>();

        if (partition.size() < k) {
            for (ProcessRecord r : partition) {
                MedDataKAnon anon = new MedDataKAnon("*","*","*", r.getDiagnosis());
                results.add(anon);
            }
        } else {
            int minAge = Integer.MAX_VALUE;
            int maxAge = Integer.MIN_VALUE;
            Set<String> genders = new HashSet<>();
            Set<String> postalCodes = new HashSet<>();
            for (ProcessRecord r : partition) {
                if (r.getAge() < minAge) {
                    minAge = r.getAge();
                }
                if (r.getAge() > maxAge) {
                    maxAge = r.getAge();
                }
                genders.add(r.getGender());
                postalCodes.add(r.getPostalCode());
            }
            String genAge = (minAge == maxAge) ? String.valueOf(minAge) : minAge + "-" + maxAge;
            String genGender = (genders.size() == 1) ? genders.iterator().next() : "*";
            String genPostal = (postalCodes.size() == 1) ? postalCodes.iterator().next() : "*"; 

            for (ProcessRecord r : partition) {
                MedDataKAnon anon = new MedDataKAnon(genAge, genGender, genPostal, r.getDiagnosis());
                results.add(anon);
            }
        }
        return results;
    }
}
