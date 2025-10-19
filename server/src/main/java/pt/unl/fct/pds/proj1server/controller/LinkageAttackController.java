package pt.unl.fct.pds.proj1server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.unl.fct.pds.proj1server.attacks.LinkageAttackService;

@RestController
@RequestMapping("/api/attack")
public class LinkageAttackController {

    @Autowired
    private LinkageAttackService linkageAttackService;

    @GetMapping("/linkage")
    public String runLinkageAttack() {
        int matches = linkageAttackService.execute();
        return "Linkage attack complete. Found " + matches + " successful re-identifications.";
    }
}