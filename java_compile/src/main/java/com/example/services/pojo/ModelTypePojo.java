/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.services.pojo;

import com.example.entity.inner.ModelArgument;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *
 * @author cyz
 * @version : ModelTypePojo.java, v 0.1 2021年03月16日 下午3:15 cyz Exp $
 */
@Data
@AllArgsConstructor
public class ModelTypePojo {
    private int id;

    private String modelTypeName;

    private String modelDetailName;

    private List<ModelArgument> arguments;

    private String modelDetailDes;

    private String modelDes;

}