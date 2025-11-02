package pt.unl.fct.pds.proj1server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pt.unl.fct.pds.proj1server.model.WorkData;
import pt.unl.fct.pds.proj1server.repository.WorkDataRepository;

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
}
