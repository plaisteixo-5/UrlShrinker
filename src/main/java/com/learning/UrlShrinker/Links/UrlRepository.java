package com.learning.UrlShrinker.Links;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("SELECT l " +
            "FROM " +
                "Link l " +
            "WHERE " +
                "l.longUrl = :url " +
                "AND l.expireDate >= :currentTime")
    Url findLinkByUrlAndNotExpired(@Param("url") String url, @Param("currentTime") LocalDateTime currentTime);
}