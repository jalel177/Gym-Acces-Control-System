package com.example.demo.service;

import com.example.demo.dao.FileRepository;
import com.example.demo.model.File;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class Fileserviceimplement implements  Fileinterface{
    @Autowired
    private FileRepository filerepo;
    private static final org.slf4j.Logger logger=LoggerFactory.getLogger(Fileserviceimplement.class.getName());
    @Override
    public ResponseEntity<?> uploadfile(String filename, MultipartFile file) {
        try {
            if (!this.fileExists(file.getOriginalFilename())) {
                File newfile = new File();
                newfile.setFilename(file.getOriginalFilename());
                newfile.setContenttype(file.getContentType());
                newfile.setSize(file.getSize());
                newfile.setData(file.getBytes());
                return new ResponseEntity<>("file uploaded succesfuly" + filerepo.save(newfile), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("file is uploaded already!", HttpStatus.CONFLICT);
            }
        }
        catch(IOException e){
            logger.error("error getting data from the file"+e.getMessage());
            return new ResponseEntity<>("error with file",HttpStatus.BAD_REQUEST);}
    }


    @Override
    public Optional<File> downloadFile(String filename) {
        return filerepo.findFileByFilename(filename);
    }



    private boolean fileExists(String filename) {
        return filerepo.existsByFilename(filename);}
}




