package com.app.ad.manager.demo.controller;

import com.app.ad.manager.demo.model.Ad;
import com.app.ad.manager.demo.model.App;
import com.app.ad.manager.demo.repository.AdReposityory;
import com.app.ad.manager.demo.repository.AppRepository;
import com.app.ad.manager.demo.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppController {

    @Autowired
    AppRepository appRepository;

    @Autowired
    AdReposityory adReposityory;

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "";
    }

    //region Application CRUDL Operations

    // Get All applications
    @RequestMapping(value = "/apps", method = RequestMethod.GET)
    public List<App> getAppList() {
        return appRepository.findAll();
    }

    // Create a new application
    @RequestMapping(value = "/apps", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public App createApp(App app) {
        return appRepository.save(app);
    }

    // Get a Single application
    @GetMapping("/apps/{id}")
    public App getAppById(@PathVariable(value = "id") Long appId) {
        return appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));
    }

    // Update a application
    @PutMapping("/apps/{id}")
    public App updateApp(@PathVariable(value = "id") Long appId,
                         @Valid @RequestBody App appDetail) {

        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));

        app.setAppId(appDetail.getAppId());
        app.setContent(appDetail.getContent());

        return appRepository.save(app);
    }

    // Delete a application
    @DeleteMapping("/apps/{id}")
    public ResponseEntity<?> deleteApp(@PathVariable(value = "id") Long appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));

        appRepository.delete(app);

        return ResponseEntity.ok().build();
    }

    //endregion

    //region Ad CRUDL Operations

    // Get all ad by app id

    @RequestMapping(value = "/apps/{appId}/ads", method = RequestMethod.GET)
    public List<Ad> getAdListByAppId(@PathVariable(value = "appId") Long appId) {
        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));

        return app.getAds();
    }

    // Create a new ad
    @RequestMapping(value = "/apps/{appId}/ads", method = RequestMethod.POST, consumes = {"application/x-www-form-urlencoded"})
    public Ad createAd(@PathVariable(value = "appId") Long appId, Ad ad) {

        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));

        ad.setApp(app);

        return adReposityory.save(ad);

    }

    // Get a Single ad
    @GetMapping("/apps/{appId}/ads/{adId}")
    public Ad getAppById(@PathVariable(value = "appId") Long appId, @PathVariable(value = "adId") Long adId) {

        App app = appRepository.findById(appId)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", appId));

        for (Ad ad : app.getAds()) {
            if (ad.getId().equals(adId)) {
                return ad;
            }
        }

        throw new ResourceNotFoundException("Ad", "id", adId);
    }

    //endregion


}