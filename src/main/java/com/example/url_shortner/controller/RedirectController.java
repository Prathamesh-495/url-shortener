package com.example.url_shortner.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.url_shortner.service.UrlMappingService;

@RestController
public class RedirectController {
    private final UrlMappingService urlMappingService;

    public RedirectController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlMappingService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", originalUrl).build();
    }
}
