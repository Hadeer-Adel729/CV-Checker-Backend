package gov.iti.jets.CV.Checker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchRequest {
        private MultipartFile file;
        private String jobDescription;
    }

