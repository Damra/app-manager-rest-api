package com.app.ad.manager.demo.repository;

import com.app.ad.manager.demo.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, Long> { }