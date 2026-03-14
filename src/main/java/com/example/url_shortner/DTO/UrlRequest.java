package com.example.url_shortner.DTO;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UrlRequest {
    @NotBlank(message = "URL cannot be empty")
    @URL(message = "Invalid URL")
    private String originalUrl;

    private String customUrl;

    private LocalDateTime expiresAt;
}
