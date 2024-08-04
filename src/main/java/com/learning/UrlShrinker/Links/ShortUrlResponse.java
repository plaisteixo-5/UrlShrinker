package com.learning.UrlShrinker.Links;

import java.time.LocalDateTime;

public record ShortUrlResponse(String longUrl, String shortUrl, LocalDateTime expirationDate) {
}
