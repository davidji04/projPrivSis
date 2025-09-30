package pt.unl.fct.pds.proj1server.controller;

import pt.unl.fct.pds.proj1server.model.WorkData;
import pt.unl.fct.pds.proj1server.model.CountRequest;
import pt.unl.fct.pds.proj1server.model.CountResponse;
import pt.unl.fct.pds.proj1server.repository.WorkDataRepository;
import pt.unl.fct.pds.proj1server.model.CountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/workdata")
public class WorkDataController {

    @Autowired
    private WorkDataRepository workDataRepository;

    @GetMapping
    public List<WorkData> getAllWorkDatas() {
        return workDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public WorkData getWorkDataById(@PathVariable Long id) {
        return workDataRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "WorkData not found with id: " + id));
    }

    @PostMapping("/count")
    public CountResponse getWorkDataCount(@RequestBody CountRequest countRequest ) {
        // TODO: Implement 
        return new CountResponse(countRequest.getAttribute(), 0);
    }
}
