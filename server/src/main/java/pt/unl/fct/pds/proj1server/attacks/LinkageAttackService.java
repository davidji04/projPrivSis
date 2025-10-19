package pt.unl.fct.pds.proj1server.attacks;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pt.unl.fct.pds.proj1server.repository.MedDataDeidentifiedRepository;
import pt.unl.fct.pds.proj1server.repository.WorkDataRepository;

@Service
public class LinkageAttackService {

    @Autowired
    private WorkDataRepository workDataRepo;

    // Autowire your de-identified repo
    @Autowired
    private MedDataDeidentifiedRepository medDataDiRepo;

  public int execute() {
        List<Object[]> identifiedUniqueGroups = workDataRepo.findUniqueQiGroups();

        List<Object[]> deidentifiedUniqueGroups = medDataDiRepo.findUniqueQiGroups();

        Set<String> identifiedSet = identifiedUniqueGroups.stream()
                .map(group -> group[0] + "-" + group[1])
                .collect(Collectors.toSet());

        Set<String> deidentifiedSet = deidentifiedUniqueGroups.stream()
                .map(group -> group[0] + "-" + group[1])
                .collect(Collectors.toSet());

        identifiedSet.retainAll(deidentifiedSet);

        return identifiedSet.size();
  }
}