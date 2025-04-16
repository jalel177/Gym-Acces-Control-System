package com.example.demo.controller;

import com.example.demo.model.File;
import com.example.demo.service.Fileinterface;
import com.example.demo.service.Fileserviceimplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private Fileinterface fileservice;
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return this.fileservice.uploadfile(file);}
    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename) {
        ResponseEntity<?> response = this.fileservice.downloadFile(filename);
        if (response.getStatusCode() == HttpStatus.OK) {
            File file = (File) response.getBody();
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename()+"\"")
                    .body(file.getData());
        }else{
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);}}

}




