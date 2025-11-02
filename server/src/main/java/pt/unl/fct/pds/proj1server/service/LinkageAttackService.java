package pt.unl.fct.pds.proj1server.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.unl.fct.pds.proj1server.repository.MedDataDeidentifiedRepository;
import pt.unl.fct.pds.proj1server.repository.MedDataKAnonRepository;
import pt.unl.fct.pds.proj1server.repository.WorkDataRepository;

@Service
public class LinkageAttackService {

    @Autowired
    private WorkDataRepository workDataRepo;

    @Autowired
    private MedDataDeidentifiedRepository medDataDiRepo;

    @Autowired
    private MedDataKAnonRepository medDataKAnonRepo;

  public int execute(Boolean onKanon) {

        List<Object[]> identifiedUniqueGroups = workDataRepo.findUniqueQiGroups();

        List<Object[]> uniqueGroups;

        if (onKanon) {
            uniqueGroups = medDataKAnonRepo.findUniqueQiGroups();
        } else {
            uniqueGroups = medDataDiRepo.findUniqueQiGroups();
        }

        Set<String> identifiedSet = identifiedUniqueGroups.stream()
                .map(group -> group[0] + "-" + group[1])
                .collect(Collectors.toSet());

        Set<String> deidentifiedSet = uniqueGroups.stream()
                .map(group -> group[0] + "-" + group[1])
                .collect(Collectors.toSet());

        identifiedSet.retainAll(deidentifiedSet);

        return identifiedSet.size();
  }
}