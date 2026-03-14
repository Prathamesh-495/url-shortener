package com.example.url_shortner.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.url_shortner.dto.UrlRequest;
import com.example.url_shortner.entity.UrlMapping;
import com.example.url_shortner.repository.UrlMappingRepository;

import jakarta.transaction.Transactional;

@Service
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;

    public UrlMappingService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMapping createShortUrl(UrlRequest request) {
        String originalUrl = request.getOriginalUrl();
        String customUrl = request.getCustomUrl();
        LocalDateTime expiresAt = request.getExpiresAt();
        UrlMapping urlMapping = new UrlMapping();
        if (customUrl != null && !customUrl.isBlank()) {
            if (urlMappingRepository.findByShortCode(customUrl).isPresent()) {
                throw new RuntimeException("custom code already exists");
            }
            urlMapping.setOriginalUrl(originalUrl);
            urlMapping.setShortCode(customUrl);
            urlMapping.setExpiresAt(expiresAt);
            return urlMappingRepository.save(urlMapping);
        } else {
            Optional<UrlMapping> existing = urlMappingRepository.findByOriginalUrl(originalUrl);
            if (existing.isPresent()) {
                return existing.get();
            }

            urlMapping.setOriginalUrl(originalUrl);
            urlMapping.setShortCode(generateShortCode());
            urlMapping.setExpiresAt(expiresAt);
            return urlMappingRepository.save(urlMapping);
        }
    }

    public String getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortCode(shortUrl)
                .orElseThrow(() -> new RuntimeException("URL not found"));
        if (urlMapping.getExpiresAt() != null && LocalDateTime.now().isAfter(urlMapping.getExpiresAt())) {
            throw new RuntimeException("URL is expired");
        }
        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        urlMappingRepository.save(urlMapping);
        return urlMapping.getOriginalUrl();
    }

    public UrlMapping getUrlByStats(String shortUrl) {
        return urlMappingRepository.findByShortCode(shortUrl).orElseThrow(() -> new RuntimeException("URL not found"));
    }

    @Transactional
    public void deleteUrl(String shortUrl) {
        urlMappingRepository.deleteByShortCode(shortUrl);
    }

    private String generateShortCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
