package pt.unl.fct.pds.proj1server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.unl.fct.pds.proj1server.model.AverageRequest;
import pt.unl.fct.pds.proj1server.model.CountRequest;
import pt.unl.fct.pds.proj1server.model.DPHistogramResponse;
import pt.unl.fct.pds.proj1server.model.DPResponse;
import pt.unl.fct.pds.proj1server.model.HistogramRequest;
import pt.unl.fct.pds.proj1server.service.DifferentialPrivacyService;
import pt.unl.fct.pds.proj1server.service.PrivacyBudgetExceededException;

@RestController
@RequestMapping("/api/dp")
public class DifferentialPrivacyController {

  @Autowired
  private DifferentialPrivacyService dpService;

  @PostMapping("/count")
  public ResponseEntity<DPResponse> getNoisyCount(@RequestBody CountRequest request) {
    try {
      DPResponse response = dpService.getNoisyCount(request);
      return ResponseEntity.ok(response);
    } catch (PrivacyBudgetExceededException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/average")
  public ResponseEntity<DPResponse> getNoisyAverage(@RequestBody AverageRequest request) {
    try {
      DPResponse response = dpService.getNoisyAverage(request);
      return ResponseEntity.ok(response);
    } catch (PrivacyBudgetExceededException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/histogram/average-by-gender")
  public ResponseEntity<DPHistogramResponse> getNoisyAvgByGender(@RequestBody HistogramRequest request) {
    try {
      DPHistogramResponse response = dpService.getNoisyAvgByGender(request);
      return ResponseEntity.ok(response);
    } catch (PrivacyBudgetExceededException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/histogram/count-by-gender")
  public ResponseEntity<DPHistogramResponse> getNoisyCountByGender(@RequestBody HistogramRequest request) {
    try {
      DPHistogramResponse response = dpService.getNoisyCountByGender(request);
      return ResponseEntity.ok(response);
    } catch (PrivacyBudgetExceededException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @PostMapping("/histogram/age-histogram")
  public ResponseEntity<DPHistogramResponse> getNoisyAgeHistogram(@RequestBody HistogramRequest request) {
    try {
      DPHistogramResponse response = dpService.getNoisyAgeHistogram(request);
      return ResponseEntity.ok(response);
    } catch (PrivacyBudgetExceededException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}