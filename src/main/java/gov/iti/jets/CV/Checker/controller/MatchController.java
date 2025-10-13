package gov.iti.jets.CV.Checker.controller;

import gov.iti.jets.CV.Checker.dto.MatchRequest;
import gov.iti.jets.CV.Checker.service.MatchService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matchCV")
@CrossOrigin(origins = "http://localhost:4200")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> match(@ModelAttribute MatchRequest request) {

            // Validate file type
            if (!request.getFile().getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest()
                        .body("{\"error\": \"Only PDF files are supported\"}");
            }
            if (request.getFile() == null || request.getFile().isEmpty()) {
                return ResponseEntity.badRequest().body("CV file is required");
            }
            if (request.getJobDescription() == null || request.getJobDescription().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Job description is required");
            }
            return ResponseEntity.ok( matchService.matchCvWithJob(request.getFile(), request.getJobDescription()));
    }
}