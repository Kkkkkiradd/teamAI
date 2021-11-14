/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.java_compile;

import com.example.controller.ConfigController;
import com.example.controller.response.Response;
import com.example.controller.vo.ConfigVO;
import com.example.entity.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cyz
 * @version : ConfigControllerTests.java, v 0.1 2021年01月17日 下午6:21 cyz Exp $
 */
@SpringBootTest
public class ConfigControllerTests {
    @Autowired
    ConfigController configController;

    @Test
    void add_config_test() {
        ConfigVO config = new ConfigVO();
        config.setFileInfoId("19");
        config.setUserId("1");
        config.setConfName("事故聚类config");
        config.setModelTypeId("2");
        List<String> filedIds = new ArrayList<>();
        filedIds.add("36");
        filedIds.add("37");
        filedIds.add("38");
        filedIds.add("39");
        filedIds.add("40");
        config.setFieldIds(filedIds);
        Response response = configController.addConfig(config);
        System.out.println(response);
    }

    @Test
    void get_all_config_test() {
        Response response = configController.getAllConfig("1");
        System.out.println(response);
    }

    @Test
    void get_all_config_new_test(){
        Response response = configController.getAllConfig_new("1");
        System.out.println(response);
    }

    @Test
    void get_one_config_test(){
        Response response = configController.getOneConfig("2");
        System.out.println(response);
    }

    @Test
    void change_config_test(){
        List<String> filedIds = new ArrayList<>();
        filedIds.add("66");
        filedIds.add("67");
        filedIds.add("68");
        filedIds.add("69");
        filedIds.add("70");
        Response response = configController.changeConfig("2","config_test1",filedIds);
        System.out.println(response);
    }

    @Test
    void get_feature_selection_test(){
        Response response = configController.getFeatureSelection("17","vehicle_class");
        System.out.println(response);
    }
}