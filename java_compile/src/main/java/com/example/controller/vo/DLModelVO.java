package com.example.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DLModelVO {
    private String id;

    private String modelTypeId; //模型的类别

    private String picFileId;  //文件信息id

    private String modelName;   //自定义的训练模型名

    private boolean isTrained;  //是否进行了训练

    private String modelPath;       //具体训练出的model

    private Map<String, String>     resOfModel; //模型训练的result

    //模型的具体参数取值
    private Map<String, String> arguments;  //模型的参数列表
}
