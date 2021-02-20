package com.main.simpleloginwithjwt.service;

import com.main.simpleloginwithjwt.entity.Version;
import com.main.simpleloginwithjwt.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VersionService {
    @Autowired
    private VersionRepository versionRepository;

    public Version getVersion() {
        return this.versionRepository.getVersion();
    }
}
