package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Deep Learning Model
 * Created by lucas on 2017/6/18.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dl_model")
public class DLModel {
    @Id
    private int id;

    private int modelTypeId; //模型的类别

    private int picFileId;  //文件信息id

    private String modelName;   //自定义的训练模型名

    private boolean isTrained;  //是否进行了训练

    private String modelPath;       //具体训练出的model

    private String     resOfModel; //模型训练的result

    //模型的具体参数取值
    private String arguments;  //模型的参数列表

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return id;
    }
}
