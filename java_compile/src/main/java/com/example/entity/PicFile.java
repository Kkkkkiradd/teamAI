package com.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "pic_file")
public class PicFile {

    @Id
    private int id;
    //foreign key which connect to User table
    private int userId;

    private String fileName;

    private String loc;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return id;
    }
    public PicFile(int userId, String fileName, String loc){
        this.userId = userId;
        this.fileName = fileName;
        this.loc = loc;
    }
}
