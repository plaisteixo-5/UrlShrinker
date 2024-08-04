package com.learning.UrlShrinker.Links;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Table(name = "urls")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    public Url(String longUrl, String shortedUrl, LocalDateTime expireDate) {
        this.longUrl = longUrl;
        this.shortUrl = shortedUrl;
        this.createdDate = LocalDateTime.now();
        this.expireDate = expireDate;
    }
}
