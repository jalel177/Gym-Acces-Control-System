package com.example.demo.service;

import com.example.demo.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface Fileinterface {


    ResponseEntity<?> uploadfile(String filename, MultipartFile file);

    Optional<File> downloadFile(String filename);
}
