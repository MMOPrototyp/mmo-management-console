package com.jukusoft.mmo.rest.api.version;

import com.jukusoft.mmo.rest.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VersionController {

    @Autowired
    private VersionService versionService;

    @PreAuthorize("hasAuthority('global.can.see.api.version')")
    @GetMapping("/api/version")
    public ResponseEntity<VersionDTO> getVersion() {
        return new ResponseEntity<>(versionService.getBackendVersion(), HttpStatus.OK);
    }

}
