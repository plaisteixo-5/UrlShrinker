package com.learning.UrlShrinker.controllers;

import com.learning.UrlShrinker.domain.Urls.ShortUrlRequest;
import com.learning.UrlShrinker.domain.Urls.ShortUrlResponse;
import com.learning.UrlShrinker.domain.Urls.ShortenedUrlResponse;
import com.learning.UrlShrinker.services.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
public class UrlController {
    
    @Autowired
    private UrlService service;
    
    @GetMapping("/shorten-url")
    public ResponseEntity<List<ShortUrlResponse>> getAllLinks(){
        return ResponseEntity.ok(service.getAllLinks());
    }
    
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @GetMapping("{shortenedUrl}")
    public void getShortedUrl(HttpServletResponse response, @PathVariable String shortenedUrl) throws IOException {
        String urlFounded = service.getShortenedUrl(shortenedUrl);
        
        if(urlFounded == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Any value was founded for the given shortened url.");
        }

        response.sendRedirect(urlFounded);
    }
    
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/shorten-url")
    public ResponseEntity<ShortUrlResponse> shortUrl(@RequestBody ShortUrlRequest longUrl) throws Exception {
        return new ResponseEntity<ShortUrlResponse>(service.shortUrl(longUrl), HttpStatus.CREATED);
    }
    
}
