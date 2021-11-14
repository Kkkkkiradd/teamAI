/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.util;

import cats.kernel.Hash;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cyz
 * @version : Json2Object.java, v 0.1 2021年02月01日 下午8:28 cyz Exp $
 */
public class Json2Object {

    public List<String> Json2List(String json){
        return JSONArray.parseArray(json,String.class);
    }

    public HashMap<String, String> Json2HashMap(String json){
        return JSON.parseObject(json,HashMap.class);
    }
}