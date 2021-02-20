package com.main.simpleloginwithjwt.repository;

import com.main.simpleloginwithjwt.entity.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, String> {
    @Query(value = "SELECT * FROM version", nativeQuery = true)
    public Version getVersion();
}
