package com.example.controller.vo;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * File Location Table
 */
@Data
public class FileInfoVO {

    private String id;

    //.parquet file location
    private String location;
    //create time
    private Date uploadTime;
    //foreign key which connect to User table
    private String userId;
    //filename
    private String filename;
    //filename
    private String              folder;
    //file structure
    private Map<String, String> fileStrucInfo;

}
