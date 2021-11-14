package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "pic_info")
public class PicInfo {


    @Id
    private int id;

    //.pic location
    private String location;
    //create time
    private Date uploadTime;

    //filename
    private String picname;

    private String tag;

    private int categoryId;

    public PicInfo() {}

    public PicInfo(String location, String picname, String tag, int categoryId) {
        this.location = location;
        this.picname = picname;
        this.uploadTime = new Date();
        this.tag = tag;
        this.categoryId = categoryId;
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
    public String getPicname() {return picname;}
    public String getTag(){return tag;}

}
