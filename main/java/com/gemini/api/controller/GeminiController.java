package com.gemini.api.controller;


 import com.gemini.api.Serice.GeminiService;
 import com.gemini.api.entity.PormptResponse;
 import com.gemini.api.repo.Repositoryp;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.HttpStatusCode;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
import com.gemini.api.entity.PromptRequest;

 import java.util.ArrayList;
 import java.util.List;


@RestController
@RequestMapping("/gemini")
public class GeminiController {

   private final GeminiService geminiService;
   @Autowired
    Repositoryp repositoryp;

    // Constructor for dependency injection
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    /**
     * Endpoint to generate text using the Gemini API.
     * Example usage: GET /gemini/generate?prompt=Write a short story about a robot.
     *
     * @param  //The text prompt from the user.
     * @return The generated text from the Gemini LLM.
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generateText(@RequestBody PromptRequest request) {
        try {
            String generatedText = geminiService.generateText(request.getPrompt());
            return ResponseEntity.ok(generatedText);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/getThis")
    public String getV(){
        return "its working fine";
    }

    @GetMapping("/history")
    public ResponseEntity<List<PormptResponse>> getHistory() {
        return ResponseEntity.ok(repositoryp.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        if (id < 0)  {
            return new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
        }
        repositoryp.deleteById(id);
        return ResponseEntity.ok(id + "Deleted successfully");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll(){
        repositoryp.deleteAll();
        return new ResponseEntity<>("Deleted all Records",HttpStatus.OK);
    }

}
