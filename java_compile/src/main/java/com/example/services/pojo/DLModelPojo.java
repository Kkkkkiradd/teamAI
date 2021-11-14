/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.services.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import java.util.HashMap;

/**
 *
 * @author cyz
 * @version : DLModelPojo.java, v 0.1 2021年03月15日 下午10:15 cyz Exp $
 */
@Data
@AllArgsConstructor
public class DLModelPojo {

    private String modelTypeId; //模型的类别

    private String picFileId;  //文件信息id

    private String modelName;   //自定义的训练模型名

    private boolean isTrained;  //是否进行了训练

    private String modelPath;       //具体训练出的model

    private HashMap<String,String> resOfModel; //模型训练的result

    //模型的具体参数取值
    private HashMap<String,String> arguments;  //模型的参数列表
}