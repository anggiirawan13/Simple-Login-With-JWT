package com.main.simpleloginwithjwt.controller;

import com.main.simpleloginwithjwt.entity.Version;
import com.main.simpleloginwithjwt.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/public/version")
public class VersionController {
    @Autowired
    private VersionService versionService;

    @GetMapping("/get")
    public Version getVersion() {
        return this.versionService.getVersion();
    }
}
