package pt.unl.fct.pds.proj1server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.unl.fct.pds.proj1server.service.AnonymityService;

@RestController
@RequestMapping("/api/kanonmeddata")
public class MedDataKanonController {
    
    @Autowired
    private AnonymityService anonymityService;

    @GetMapping("/anonymize")
    public String runKAnonymization() {
        anonymityService.runAnonymization();
        return "K-anonymization process on medData completed.";
    }
}
