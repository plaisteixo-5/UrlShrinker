package com.learning.UrlShrinker.Links;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UrlController {
    
    @Autowired
    private UrlService service;
    
    @GetMapping("/shorten-url")
    public ResponseEntity<List<ShortUrlResponse>> getAllLinks(){
        return ResponseEntity.ok(service.getAllLinks());
    }
    
    @GetMapping("/shorten-url/{url}")
    public ResponseEntity<ShortenedUrlResponse> getShortedUrl(@PathVariable String shortedUrl){
        return ResponseEntity.ok(service.getShortenedUrl(shortedUrl));
    }
    
    @PostMapping("/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortUrl(@RequestBody ShortUrlRequest longUrl){
        return ResponseEntity.ok(service.shortUrl(longUrl));
    }
    
}
