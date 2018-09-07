package com.app.ad.manager.demo.repository;

import com.app.ad.manager.demo.model.Ad;
import com.app.ad.manager.demo.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    Optional<List<Ad>> findAdsByAppId(Long appId);
    Optional<Ad> findAdByAppIdAndId(Long appId, Long id);
}