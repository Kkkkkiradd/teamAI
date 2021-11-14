/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.java_compile;

import com.example.controller.ModelController;
import com.example.controller.response.Response;
import com.example.controller.vo.DLModelVO;
import com.example.controller.vo.ModelVO;
import com.example.dao.ModelTypeDao;
import com.example.entity.DLModel;
import com.example.entity.Model;
import com.example.entity.ModelType;
import com.example.util.Json2Object;
import com.google.gson.JsonObject;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author cyz
 * @version : ModelControllerTests.java, v 0.1 2021年01月15日 下午5:11 cyz Exp $
 */
@SpringBootTest
class ModelControllerTests {

    @Autowired
    ModelTypeDao modelTypeDao;

    @Autowired
    ModelController modelController;

    @Test
    void test() {
        List<ModelType> list = modelTypeDao.findByModelTypeName("cluster");
        list.stream().forEach(e -> System.out.println(e.toString()));

    }

    @Test
    void add_model_test() {
        ModelVO model = new ModelVO();
        model.setModelName("事故聚类K-Means");
        model.setModelTypeId("2");
        model.setConfigId("4");
        model.setFileInfoId("19");
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("Seed", "10");
        arguments.put("NumOfCluster", "10");
        model.setArguments(arguments);
        Response response = modelController.addModel(model);
        System.out.println(response);
    }

    @Test
    void get_all_model_test() {
        Response response = modelController.getAllModel("1");
        System.out.println(response);
    }


    //DLmodel
    @Test
    void add_dl_model_test(){
        DLModelVO dlModelVO = new DLModelVO();
        dlModelVO.setModelName("dl_model_test");
        dlModelVO.setModelTypeId("10");
        dlModelVO.setPicFileId("4");
        HashMap<String, String> arguments = new HashMap<>();
        arguments.put("steps_per_epoch", "200");
        arguments.put("valid_steps", "200");
        arguments.put("epochs","6");
        dlModelVO.setArguments(arguments);
        Response response = modelController.addDLModel(dlModelVO);
        System.out.println(response);
    }

    @Test
    void get_all_dl_model_test(){
        Response response = modelController.getAllDLModel("1");
        System.out.println(response);
    }

    @Test
    void get_dl_detail_model_test(){
        Response response = modelController.getDLDetailModel("3");
        System.out.println(response);
    }






    @Test
    void change_model_test(){
        Response response = modelController.changeModel("1","model_test","7");
        System.out.println(response);
    }

    @Test
    void get_model_type_test(){
        Response response = modelController.getModelType("dl");
        System.out.println(response);
    }

    @Test
    void train_model_test() {
        Response response = modelController.trainModel("6");
        System.out.println(response);
    }

    @Test
    void delete_model_test(){
        Response response = modelController.deleteModelByModelId("2");
        System.out.println(response);
    }

    @Test
    void upload_testfile_test() throws IOException {
        File pdfFile = new File("/Users/cyz/Documents/idea_workspace/Desktop/java_compile/src/main/file/event_test.csv");
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Response response = modelController.uploadTestFile("1",multipartFile,"event_test.csv");
        System.out.println(response);
    }
    @Test
    void use_model_test() throws IOException {
        Response response = modelController.useModel("29","6");
        System.out.println(response);
    }

    //@Test
    //void test1() throws IOException{
    //    List<ModelType> modelTypes = modelTypeDao.findByModelTypeName("cluster");
    //    HashMap<String,String> m = new Json2Object().Json2HashMap(modelTypes.get(0).getArguments());
    //    HashMap<String,HashMap<String,String>> mm = new HashMap<>();
    //    for(String key:m.keySet()) {
    //        System.out.println(m.get("Seed"));
    //        mm.put(key, new Json2Object().Json2HashMap(m.get(key)));
    //    }
    //
    //}

}