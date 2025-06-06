package com.example.demo.controller;

import com.example.demo.model.File;
import com.example.demo.service.Fileinterface;
import com.example.demo.service.Fileserviceimplement;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private Fileinterface fileservice;
    @PostMapping("/uploadfile/{filename}")
    public ResponseEntity<?> uploadFile(
            @PathVariable("filename") String filename, // ✅ Correct variable name
            @RequestParam("file") MultipartFile file // Use @RequestParam for file
    ) {
        return this.fileservice.uploadfile(filename, file); // Pass filename to service
    }
    @GetMapping("download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        Optional<File> optionalFile = fileservice.downloadFile(filename);
        if (optionalFile.isPresent()) {
            File file = optionalFile.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getContenttype()))
                    // Pas besoin de "attachment" si tu veux l’afficher dans <img>
                    .body(file.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }}






