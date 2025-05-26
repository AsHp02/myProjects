package com.gemini.api.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Prompts")
public class PormptResponse {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    private String response;
    private LocalDateTime timestamp;


    public PormptResponse(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
        this.timestamp = LocalDateTime.now();
    }

    public PormptResponse() {
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PormptResponse{" +
                "id=" + id +
                ", prompt='" + prompt + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Long getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getResponse() {
        return response;
    }
}
