package pt.unl.fct.pds.proj1server.attacks;

import java.util.List;

// Classe que encapsula o resultado do Linkage Attack
public class LinkageAttackResponse {

  private int totalMatches;
  private int totalRecords;
  private double successRatePercentage;
  private List<LinkageResult> matches;

  // Construtor
  public LinkageAttackResponse(int totalMatches, int totalRecords, double successRatePercentage,
      List<LinkageResult> matches) {
    this.totalMatches = totalMatches;
    this.totalRecords = totalRecords;
    this.successRatePercentage = successRatePercentage;
    this.matches = matches;
  }

  // Getters e Setters (Necessários para serialização JSON pelo Spring)
  public int getTotalMatches() {
    return totalMatches;
  }

  public void setTotalMatches(int totalMatches) {
    this.totalMatches = totalMatches;
  }

  public int getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(int totalRecords) {
    this.totalRecords = totalRecords;
  }

  public double getSuccessRatePercentage() {
    return successRatePercentage;
  }

  public void setSuccessRatePercentage(double successRatePercentage) {
    this.successRatePercentage = successRatePercentage;
  }

  public List<LinkageResult> getMatches() {
    return matches;
  }

  public void setMatches(List<LinkageResult> matches) {
    this.matches = matches;
  }
}