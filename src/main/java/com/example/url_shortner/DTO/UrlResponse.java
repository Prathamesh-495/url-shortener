package com.example.url_shortner.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrlResponse {
    private String shortCode;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private int clickCount;

}
