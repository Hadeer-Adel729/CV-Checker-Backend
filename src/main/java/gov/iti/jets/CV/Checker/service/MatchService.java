
package gov.iti.jets.CV.Checker.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import gov.iti.jets.CV.Checker.dto.MatchedResult;
import gov.iti.jets.CV.Checker.mapper.JsonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MatchService {

    private final FileService fileService;
    private final JsonMapper  jsonMapper;

    @Value("${gemini.api.key}")
    String apiKey;

    @Value("${gemini.modelName}")
    String modelName;

    public MatchService(FileService fileService, JsonMapper jsonMapper) {
        this.fileService = fileService;
        this.jsonMapper = jsonMapper;
    }

    String promptText =
            "Analyze this CV and compare it with the job description. Return JSON with: " +
                    "{\"matchScore\": number (0-100 percentage), \"strengths\": [], \"weaknesses\": [], " +
                    "\"improvements\": [], \"summary\": \"text\"}. " +
                    "CV: %s " + "Job Description: %s";


    public MatchedResult matchCvWithJob(MultipartFile file, String jobDescription) {
        try {
            // Extract text from PDF
            String cvText = fileService.extractTextFromPdf(file);

            String prompt = String.format( promptText, cvText, jobDescription );

            try {
                Client client = Client.builder().apiKey(apiKey).build();
                GenerateContentResponse response = client.models.generateContent(
                        modelName,
                        prompt,
                        null
                );

                // Parse JSON response into MatchResultDTO
                return jsonMapper.parseJsonResponse(response.text());

            }catch (RuntimeException e){
                throw new RuntimeException("The model is overloaded. Please try again later." );
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing PDF file: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error analyzing CV: " + e.getMessage(), e);
        }
    }
}