package com.learning.UrlShrinker.Links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class UrlService {
    private static final String UPPER_CASE_ALPHABET_AND_NUMBERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = UPPER_CASE_ALPHABET_AND_NUMBERS.length();
    private static final Random rand = new Random();
    private static final String STRING_KEY_PREFIX = "redi2read:strings:";
    
    @Value("${url.expiration.seconds}")
    private String expiration;
    
    @Autowired
    private UrlRepository repository;
    @Autowired
    private RedisTemplate<String, String> template;
    
    public ShortUrlResponse shortUrl(ShortUrlRequest data){
        String formatedUrl = this.CleanUrl(data.longUrl());
        
        String shortedUrl = this.shortUrlAlgorithm(formatedUrl);
        LocalDateTime expireDate = LocalDateTime.now().plusSeconds(Integer.parseInt(expiration));
        
        Url newLink = new Url(data.longUrl(), shortedUrl, expireDate);
        
        Url linkSaved = repository.save(newLink);
        
        if(linkSaved.getId() == null)
        {
            System.out.println("Ocorreu um erro ao gravar registro.");
        }
        
        template.opsForValue().set(STRING_KEY_PREFIX + newLink.getShortUrl(), newLink.getLongUrl());
        
        return new ShortUrlResponse(linkSaved.getLongUrl(), linkSaved.getShortUrl(), linkSaved.getExpireDate());
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
        List<Url> links = repository.findAll();
        
        return links.stream().map(link -> new ShortUrlResponse(link.getLongUrl(), link.getShortUrl(), link.getExpireDate())).toList();
    }

    public ShortenedUrlResponse getShortenedUrl(String shortedUrl) {
        Url link = repository.findLinkByUrlAndNotExpired(shortedUrl, LocalDateTime.now());
        
        if(link.getId() == null) {
            System.out.println("Link não localizado.");
        }
        
        return new ShortenedUrlResponse(link.getShortUrl(), link.getExpireDate());
    }
}
