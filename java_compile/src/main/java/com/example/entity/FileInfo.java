package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * File Location Table
 */
@Data
@Entity
@Table(name = "file_info")
public class FileInfo {

    @Id
    private int id;

    //.parquet file location
    private String location;
    //create time
    private Date uploadTime;
    //foreign key which connect to User table
    private int userId;
    //filename
    private String filename;
    //filename
    private String              folder;
    //file structure
    private String fileStrucInfo;

    public FileInfo() {}

    public FileInfo(String location, int userId, String filename) {
        this.location = location;
        this.userId = userId;
        this.filename = filename;
        this.uploadTime = new Date();
    }
    //for the scala use
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public String getLocation() {
        return location;
    }
    public String getFilename() {return filename;}
    public void setFileStrucInfo(String fileStrucInfo)
    {
        this.fileStrucInfo = fileStrucInfo;
    }
}
