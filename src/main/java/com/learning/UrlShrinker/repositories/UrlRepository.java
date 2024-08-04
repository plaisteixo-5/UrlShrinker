package com.learning.UrlShrinker.repositories;

import com.learning.UrlShrinker.domain.Urls.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

}