# Gemini API Integration with Spring Boot

A Spring Boot application that integrates with Google's Gemini AI model to provide text generation capabilities via REST API endpoints. The application includes features for generating AI responses and maintaining a history of interactions.

## Key Features

- Text generation using Google's Gemini AI model (gemini-2.0-flash)
- REST API endpoints for prompt submission and response generation
- Persistent storage of all prompts and responses using MySQL
- History tracking of all AI interactions

## Technical Stack

- Java 17
- Spring Boot 3.2.5
- MySQL Database
- Spring Data JPA
- Jackson for JSON processing
- Maven for dependency management

## API Endpoints

- `POST /gemini/generate` - Generate AI response for a given prompt
- `GET /gemini/history` - Retrieve history of all prompt-response interactions

## Setup Requirements

- Java 17 or higher
- MySQL Server
- Google Gemini API key
