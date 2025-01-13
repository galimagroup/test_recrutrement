package com.demba.back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demba.back.services.FileStorageService;

@RestController
@RequestMapping("files")
public class FileController {

  @Autowired
  private FileStorageService fileStorageService;

  @GetMapping("/read")
  public ResponseEntity<Resource> getFile(@RequestParam String url)
    throws Exception {
    Resource resource = new UrlResource(fileStorageService.getRealPath(url).toUri());
    return ResponseEntity
      .ok()
      .contentType(fileStorageService.getMediaType(url))
      .body(resource);
  }
}
