package com.example.services.pojo;

import com.example.entity.HeaderInfo;
import com.example.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by twenbo on 2016/11/17.
 * Changed by Lucas on 2016/11/22.
 */

@Data
@AllArgsConstructor
public class ConfigDetilePojo {

    private String configId;

    private String modelTypeId;

    private String modelTypeName;
    private String modelDes;
    private String modelDetileDes;
    //
    private List<HeaderInfo> featureList;

    private List<ConfigModelPojo> modelInfos;

}
