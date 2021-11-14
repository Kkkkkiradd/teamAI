/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.util;

import com.alibaba.fastjson.JSON;


import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cyz
 * @version : Object2Json.java, v 0.1 2021年02月01日 下午7:29 cyz Exp $
 */
public class Object2Json<E> {

    public String HashMap2Json(HashMap<E,E> hm){
        return JSON.toJSONString(hm);
    }

    public String List2Json(List<E> list){
        return JSON.toJSONString(list);
    }

    public String Array2Json(E[] array){
        return JSON.toJSONString(array);
    }
}