package pt.unl.fct.pds.proj1server.attacks;

import java.util.*;

import org.hibernate.boot.registry.classloading.spi.ClassLoaderService.Work;

import pt.unl.fct.pds.proj1server.deidentification.MedDataDeidentified;
import pt.unl.fct.pds.proj1server.deidentification.MedDataDeidentified.MedDataDeidentifiedRepository;
import pt.unl.fct.pds.proj1server.repository.WorkDataRepository;

public class LinkageAttackService {

  private final MedDataDeidentifiedRepository medRepo;
  private final WorkDataRepository workRepo;

  public LinkageAttackService(MedDataDeidentifiedRepository medRepo, WorkDataRepository workRepo) {
    this.medRepo = medRepo;
    this.workRepo = workRepo;
  }

  public void performLinkageAttack() {

  }
}