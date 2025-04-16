package com.example.demo.dao;

import com.example.demo.model.File;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FileRepository extends JpaRepository<File,Long> {
    @Query("SELECT f from  File f WHERE f.fileid=?1")
    Optional<File> findFileByFileid (Long fileid) ;
    @Query("SELECT f from  File f WHERE f.filename=?1")
     Optional<File> findFileByFilename (String filename) ;
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END " +
            "FROM File f WHERE f.filename = :filename")
    boolean existsByFilename(@Param("filename") String filename);



}
