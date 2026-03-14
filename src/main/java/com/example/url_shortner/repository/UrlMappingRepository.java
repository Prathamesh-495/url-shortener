package com.example.url_shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.url_shortner.entity.UrlMapping;

public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {

    Optional<UrlMapping> findByShortCode(String shortcode);

    Optional<UrlMapping> findByOriginalUrl(String orginalUrl);

    Optional<UrlMapping> findByClickCount(int clickCount);

    void deleteByShortCode(String shortcode);

    UrlMapping save(String customUrl);
}
