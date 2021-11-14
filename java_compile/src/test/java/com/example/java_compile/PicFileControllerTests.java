/**
 * Alipay.com Inc. Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.example.java_compile;

import com.example.controller.PicController;
import com.example.controller.response.Response;
import com.example.model.helper.Stringcyz;
import com.example.services.PictureService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author cyz
 * @version : PicFileControllerTests.java, v 0.1 2021年02月09日 下午3:10 cyz Exp $
 */
@SpringBootTest
public class PicFileControllerTests {
    @Autowired
    PictureService pictureService;
    @Autowired
    Stringcyz stringcyz;
    @Test
    public void picfile_upload_test() throws IOException {
        boolean res = pictureService.parseZipFile("dogcat.zip",1,"/Users/cyz/Documents/idea_workspace/Desktop/java_compile/src/main/picfile/");
        System.out.println(res);
    }


    @Test
    public void aaa() throws IOException {
        stringcyz.main();

    }

}