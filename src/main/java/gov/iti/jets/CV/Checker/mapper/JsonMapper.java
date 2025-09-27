package gov.iti.jets.CV.Checker.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.iti.jets.CV.Checker.dto.MatchedResult;
import org.springframework.stereotype.Component;

@Component
public class JsonMapper {

    ObjectMapper objectMapper = new ObjectMapper();

    public MatchedResult parseJsonResponse(String jsonResponse) throws JsonProcessingException {

        // Clean the response in case there's any extra text
        String cleanJson = jsonResponse.trim();

        // Remove markdown code blocks if present
        if (cleanJson.startsWith("```json")) {
            cleanJson = cleanJson.substring(7);
        }
        if (cleanJson.endsWith("```")) {
            cleanJson = cleanJson.substring(0, cleanJson.length() - 3);
        }
        cleanJson = cleanJson.trim();

        return objectMapper.readValue(cleanJson, MatchedResult.class);

    }
}

