package com.example.url_shortner.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.url_shortner.DTO.UrlRequest;
import com.example.url_shortner.DTO.UrlResponse;
import com.example.url_shortner.entity.UrlMapping;
import com.example.url_shortner.service.UrlMappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    @Value("${app.base.url:http://localhost:8080}")
    private String baseUrl;

    public UrlMappingController(UrlMappingService urlMappingService) {
        this.urlMappingService = urlMappingService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest request) {
        UrlMapping urlMapping = urlMappingService.createShortUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(urlMapping));
    }

    @GetMapping("/urls/{shortCode}/stats")
    public ResponseEntity<UrlResponse> getUrlStats(@PathVariable String shortCode) {
        UrlMapping urlMapping = urlMappingService.getUrlByStats(shortCode);
        return ResponseEntity.status(HttpStatus.OK).body(mapToResponse(urlMapping));
    }

    @DeleteMapping("/urls/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode) {
        urlMappingService.deleteUrl(shortCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private UrlResponse mapToResponse(UrlMapping urlMapping) {
        UrlResponse response = new UrlResponse();
        response.setShortUrl(baseUrl + "/" + urlMapping.getShortCode());
        response.setOriginalUrl(urlMapping.getOriginalUrl());
        response.setCreatedAt(urlMapping.getCreatedAt());
        response.setClickCount(urlMapping.getClickCount());
        response.setShortCode(urlMapping.getShortCode());
        return response;
    }
}
