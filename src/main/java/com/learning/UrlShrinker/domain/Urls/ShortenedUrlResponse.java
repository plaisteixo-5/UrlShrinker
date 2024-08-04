package com.learning.UrlShrinker.domain.Urls;

import java.time.LocalDateTime;

public record ShortenedUrlResponse(String shortUrl, LocalDateTime expirationDate) {
}
