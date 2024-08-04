package com.learning.UrlShrinker.services;

import com.learning.UrlShrinker.domain.Urls.ShortUrlRequest;
import com.learning.UrlShrinker.domain.Urls.ShortUrlResponse;
import com.learning.UrlShrinker.domain.Urls.ShortenedUrlResponse;
import com.learning.UrlShrinker.domain.Urls.Url;
import com.learning.UrlShrinker.repositories.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlService {
    private static final String UPPER_CASE_ALPHABET_AND_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = UPPER_CASE_ALPHABET_AND_NUMBERS.length();
    private static final Random rand = new Random();
    private static final String URL_REGEX =
            "^(http|https)://"
                    + "([a-zA-Z0-9\\-\\.]+)\\.([a-zA-Z]{2,5})"
                    + "(:[0-9]{1,5})?"
                    + "(/.*)?$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    
    @Value("${url.expiration.seconds}")
    private String expiration;
    
    @Autowired
    private RedisService redisService;
    
    @Autowired
    private UrlRepository repository;
    
    public ShortUrlResponse shortUrl(ShortUrlRequest data) throws Exception {
        this.ValidateUrl(data.longUrl());
        String formatedUrl = this.CleanUrl(data.longUrl());
        
        String shortedUrl = this.shortUrlAlgorithm(formatedUrl);
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(Integer.parseInt(expiration));
        
        Url newUrl = new Url(data.longUrl(), shortedUrl, expireDate);
        
        Url linkSaved = repository.save(newUrl);
        
        if(linkSaved.getId() == null)
        {
            throw new Exception("Generic error.");
        }

        redisService.setValue(newUrl.getShortUrl(), newUrl.getLongUrl());
        
        return new ShortUrlResponse(linkSaved.getLongUrl(), linkSaved.getShortUrl(), linkSaved.getExpireDate());
    }

    private void ValidateUrl(String rawUrl) throws Exception {
        if (rawUrl == null || rawUrl.isEmpty()) {
            throw new Exception("Empty url.");
        }

        Matcher matcher = URL_PATTERN.matcher(rawUrl);
        if (!matcher.matches()) {
            throw new Exception("Invalid url");
        }
    }

    private String CleanUrl(String rawUrl) {
        String httpsToRemove = "https://";
        int httpsStrPos = rawUrl.indexOf(httpsToRemove);
        
        if(httpsStrPos == 0) {
            return rawUrl.substring(httpsToRemove.length());
        }
        
        return rawUrl;
    }

    private String shortUrlAlgorithm(String url) {
        StringBuilder urlShorted = new StringBuilder();
        
        byte[] hash = Base64.getUrlEncoder().withoutPadding().encode(url.getBytes(StandardCharsets.UTF_8));
        
        for(byte b : hash) {
            urlShorted.append(UPPER_CASE_ALPHABET_AND_NUMBERS.charAt(b % BASE));
        }
        
        return urlShorted.substring(0, rand.nextInt(6) + 5);
    }

    public List<ShortUrlResponse> getAllLinks() {
        List<Url> urls = repository.findAll();
        
        return urls.stream().map(url -> new ShortUrlResponse(url.getLongUrl(), url.getShortUrl(), url.getExpireDate())).toList();
    }

    public String getShortenedUrl(String shortedUrl) {
        // Url link = repository.findUrlByUrlAndNotExpired(shortedUrl, LocalDateTime.now());
        String shortenedUrlSearched = redisService.getValue(shortedUrl);
        
        if(shortenedUrlSearched == null) {
            System.out.println("Url not found.");
        }
        
        return shortenedUrlSearched;
    }
}
