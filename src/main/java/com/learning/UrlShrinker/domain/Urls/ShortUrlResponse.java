package com.learning.UrlShrinker.domain.Urls;

import java.time.LocalDateTime;

public record ShortUrlResponse(String longUrl, String shortUrl, LocalDateTime expirationDate) {
}
