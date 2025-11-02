package pt.unl.fct.pds.proj1server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.unl.fct.pds.proj1server.model.LinkageResponse;
import pt.unl.fct.pds.proj1server.service.LinkageAttackService;

@RestController
@RequestMapping("/api/attack")
public class LinkageAttackController {

    @Autowired
    private LinkageAttackService linkageAttackService;
  
    @GetMapping("/linkageOnDiidentified")
    public ResponseEntity<LinkageResponse> runLinkageAttackOnDiidentified() {
        int matches = linkageAttackService.execute(false);
        LinkageResponse response = new LinkageResponse(
            "Linkage attack on De-identified data complete.",
            matches
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/linkageOnKanon")
    public ResponseEntity<LinkageResponse> runLinkageAttackOnKanon() {
        int matches = linkageAttackService.execute(true);
        LinkageResponse response = new LinkageResponse(
            "Linkage attack on K-Anonymized data complete.",
            matches
        );
        return ResponseEntity.ok(response);
    }
}