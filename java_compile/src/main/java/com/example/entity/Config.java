package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "config")
public class Config {

    @Id
    private int id;

    private int fileInfoId;      //文件id

    private int userId;          //用户Id

    private int modelTypeId;     //模型类id

    private String confName;        //自定义模型名

    private String fieldIds;   //选择训练的特征及特征描述

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return id;
    }
    public String getFieldIds(){
        return fieldIds;
    }
}
