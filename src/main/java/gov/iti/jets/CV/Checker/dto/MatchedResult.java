package gov.iti.jets.CV.Checker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchedResult {
    private double matchScore;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> improvements;
    private String summary;

    @Override
    public String toString() {
        return "MatchResultDTO{" +
                "matchScore=" + matchScore +
                ", strengths=" + strengths +
                ", weaknesses=" + weaknesses +
                ", improvements=" + improvements +
                ", summary='" + summary + '\'' +
                '}';
    }
}
