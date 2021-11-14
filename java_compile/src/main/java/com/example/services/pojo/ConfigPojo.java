package com.example.services.pojo;

import com.example.entity.HeaderInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class ConfigPojo {

    private String fileId;

    private String fileName;

    private String configId;

    private String modelTypeId;

    private String modelTypeName;

    //private String modelDes;

    private String confName;

    //private List<String> featureList;

    //
    private List<HeaderInfo> featureList;
}
