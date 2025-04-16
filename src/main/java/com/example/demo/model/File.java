package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="fileid")
    private Long fileid;
    private String filename;
    private String contenttype;
    private Long size;
    @Lob
    private byte[] data;


}
