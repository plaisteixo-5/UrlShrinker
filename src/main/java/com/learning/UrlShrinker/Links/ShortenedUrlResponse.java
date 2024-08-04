package com.learning.UrlShrinker.Links;

import java.time.LocalDateTime;

public record ShortenedUrlResponse(String shortUrl, LocalDateTime expirationDate) {
}
