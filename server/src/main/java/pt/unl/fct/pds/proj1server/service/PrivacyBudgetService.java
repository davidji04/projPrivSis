package pt.unl.fct.pds.proj1server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class PrivacyBudgetService {

  @Value("${dp.privacy.total_budget:1.0}")
  private double totalBudget;
  private double remainingBudget = totalBudget;

  @PostConstruct
  public void init() {
    this.remainingBudget = this.totalBudget;
  }

  public synchronized void consumeBudget(double amount) throws PrivacyBudgetExceededException {
    if (amount > this.remainingBudget) {
      throw new PrivacyBudgetExceededException(
          "Not enough privacy budget remaining. Requested: " + amount + ", Remaining: " + this.remainingBudget);
    }

    this.remainingBudget -= amount;
  }

  public double getRemainingBudget() {
    return remainingBudget;
  }

}
