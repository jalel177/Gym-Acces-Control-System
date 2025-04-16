package com.example.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface Fileinterface {
    ResponseEntity<?> uploadfile(MultipartFile file);

    ResponseEntity<?> downloadFile(String filename);
}
