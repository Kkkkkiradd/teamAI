package com.example.java_compile;

import com.example.analysis.FileAnalysis;
import com.example.controller.FileController;
import com.example.controller.LoginController;
import com.example.controller.response.Response;
import com.example.dao.FileInfoDao;
import com.example.entity.FileInfo;
import com.example.services.FileService;
import com.example.services.LoginService;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@SpringBootTest
class FileControllerTests {

    @Autowired
    LoginController loginController;
    @Autowired
    FileController fileController;
    @Autowired
    FileService fileService;
    @Autowired
    FileInfoDao fileInfoDao;
    @Autowired
    FileAnalysis fileAnalysis;
    @Test
    void login_Test(){
        Response response = loginController.userLogin("ccnt","123");

        System.out.println(response.toString());
    }

    @Test
    void upload_Test() throws IOException {
        File pdfFile = new File("/Users/cyz/Documents/idea_workspace/Desktop/java_compile/src/main/file/event_data.csv");
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        Response response = fileController.fileLoader("1",multipartFile,"event_data.csv");
        System.out.println(response.toString());

    }

    @Test
    void delete_Test(){
        Response response = fileController.delete("3");
        fileController.delete("4");
        System.out.println(response.toString());
    }
    @Test
    void file_get_all_Test(){
        Response response = fileController.getAll("1");
        System.out.println(response.toString());
    }

    @Test
    void file_get_detail_Test() {
        Response response = fileController.getDetail("10");
        System.out.println(response.toString());
    }

    @Test
    void analysis_Test(){
        Response response = fileController.analysis("17");
        System.out.println(response.toString());
    }

    @Test
    void analysis_func_Test(){
        String[] fieldIds = {"37"};
        Response response = fileController.analysisOfFields1(fieldIds,"1");
        System.out.println(response.toString());
    }

    @Test
    void analysis_classification_Test() throws Exception {
        int field = 64;
        String[] fieldIds = {"58","59","60","61",
                "62","63"};
       System.out.println(fileService.getFieldClassificationAnalysis(field,fieldIds,6));
    }

    @Test
    void analysis_classification_statistical_Test() throws Exception {
        int field = 37;
        String[] fieldIds = {"38"};
        System.out.println(fileAnalysis.getFieldDistribution2(field,fieldIds));
    }

    @Test
    void getAllFileAndFunc_Test(){
        Response response = fileController.getAllFileAndFunc("1");
        System.out.println(response.toString());
    }

}
