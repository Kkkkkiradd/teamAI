package com.example.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigVO {

    private String id;

    private String fileInfoId;      //文件id

    private String userId;          //用户Id

    private String modelTypeId;     //模型类id

    private String confName;        //自定义模型名

    private List<String> fieldIds;   //选择训练的特征及特征描述

    public String getId(){
        return id;
    }
    public List<String> getFieldIds(){
        return fieldIds;
    }
}
