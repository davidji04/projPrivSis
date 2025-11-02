package pt.unl.fct.pds.proj1server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.unl.fct.pds.proj1server.service.LinkageAttackService;

@RestController
@RequestMapping("/api/attack")
public class LinkageAttackController {

    @Autowired
    private LinkageAttackService linkageAttackService;

    @GetMapping("/linkageOnDiidentified")
    public String runLinkageAttackOnDiidentified() {
        int matches = linkageAttackService.execute(false);
        return "Linkage attack on De-identified data complete. Found " + matches + " successful re-identifications.";
    }
    @GetMapping("/linkageOnKanon")
    public String runLinkageAttackOnKanon() {
        int matches = linkageAttackService.execute(true);
        return "Linkage attack on K-Anonymized data complete. Found " + matches + " successful re-identifications.";
    }
}