package com.example.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.analysis.FeatureProject;
import com.example.controller.vo.ConfigVO;
import com.example.dao.*;
import com.example.entity.Config;
import com.example.entity.HeaderInfo;
import com.example.entity.Model;
import com.example.entity.ModelType;
import com.example.services.pojo.ConfigDetilePojo;
import com.example.services.pojo.ConfigModelPojo;
import com.example.services.pojo.ConfigPojo;
import com.example.services.pojo._ConfigPojo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Config-related service Created by twenbo on 2016/11/16. Changed by lucas on 2016/11/18.
 */
@Slf4j
@Component
@Data
public class                ConfigService {

    @Autowired
    private ConfigDao      configDao;
    @Autowired
    private FileInfoDao    fileInfoDao;
    @Autowired
    private ModelTypeDao   modelTypeDao;
    @Autowired
    private HeaderInfoDao  headerInfoDao;
    @Autowired
    private ModelDao       modelDao;
    @Autowired
    private FeatureProject featureProject;

    /**
     * @param configVO
     * @return
     */

    public String addConfig(ConfigVO configVO) {

        Config configInfo = new Config();
        configInfo.setFileInfoId(Integer.parseInt(configVO.getFileInfoId()));
        configInfo.setUserId(Integer.parseInt(configVO.getUserId()));
        configInfo.setModelTypeId(Integer.parseInt(configVO.getModelTypeId()));
        configInfo.setConfName(configVO.getConfName());
        configInfo.setFieldIds(JSON.toJSON(configVO.getFieldIds()).toString());
        val configId = configDao.save(configInfo).getId();

        return String.valueOf(configId);
    }

    /**
     * @param userId
     * @return
     */
    public List<ConfigPojo> getAllConfigs(int userId) {

        val rsl = configDao.findByUserId(userId).stream().map((config) -> {
            String fileName = fileInfoDao.findById(config.getFileInfoId()).getFilename();
            String modelTypeName = modelTypeDao.findById(config.getModelTypeId()) == null ? "" :
                    modelTypeDao.findById(config.getModelTypeId()).getModelTypeName();
            ConfigPojo configPojo = new ConfigPojo(String.valueOf(config.getFileInfoId()),
                    fileName,
                    String.valueOf(config.getId()),
                    String.valueOf(config.getModelTypeId()),
                    modelTypeName,
                    config.getConfName(),
                    JSONObject.parseArray(config.getFieldIds(), String.class).stream().map(fieldId ->
                            headerInfoDao.findById(Integer.parseInt(fieldId))
                    ).collect(Collectors.toList()));
            return configPojo;
        }).collect(Collectors.toList());
        return rsl;
    }

    /**
     * @param userId
     * @return
     */
    public List<_ConfigPojo> _getAllConfigsByUserId(int userId) {

        val rsl = configDao.findByUserId(userId).stream().map((config) -> {
            String fileName = fileInfoDao.findById(config.getFileInfoId()).getFilename();
            _ConfigPojo configPojo = new _ConfigPojo(String.valueOf(config.getFileInfoId()),
                    fileName,
                    String.valueOf(config.getId()),
                    config.getConfName());
            return configPojo;
        }).collect(Collectors.toList());
        return rsl;
    }

    /**
     * @param configId
     * @return
     */
    public ConfigDetilePojo getConfigInfoByConfigId(int configId) {
        List<ConfigModelPojo> modelInfos = new ArrayList<>();
        List<Model> models = modelDao.findByConfigId(configId);
        String modelTypeName = "";
        String modelTypeDes = "";
        int modelTypeId = 0;
        for (int i = 0; i < models.size(); i++) {
            Model model = models.get(i);
            ModelType modelType = modelTypeDao.findById(model.getModelTypeId());
            int flag = -1;
            if (model.isTrained() && model.getResOfModel() != null) {
                flag = 1;
            } else if (model.isTrained() && model.getResOfModel() == null) {
                flag = 0;
            } else {
                flag = -1;
            }
            modelInfos.add(new ConfigModelPojo(
                    model.getModelName(), modelType == null ? "自动训练" : modelType.getModelDetailName(), flag
            ));
            modelTypeId = modelType == null ? 0 : modelType.getId();
            modelTypeName = modelType == null ? "dl" : modelType.getModelTypeName();
            modelTypeDes = modelType == null ? "" : modelType.getModelDes();
        }

        val config = configDao.findById(configId);
        //        String modelTypeName = modelTypeDao.findById(config.getModelTypeId()) == null ? "" :
        //                modelTypeDao.findById(config.getModelTypeId()).getModelTypeName();
        //        String modelTypeDes = modelTypeDao.findById(config.getModelTypeId()) == null ? "" :
        //                modelTypeDao.findById(config.getModelTypeId()).getModelDes();
        String modelDetileDes = modelTypeDao.findById(config.getModelTypeId()) == null ? "" :
                modelTypeDao.findById(config.getModelTypeId()).getModelDetailDes();
        ConfigDetilePojo configPojo = new ConfigDetilePojo(
                String.valueOf(config.getId()),
                String.valueOf(modelTypeId),
                modelTypeName,
                modelTypeDes,
                modelDetileDes,
                JSONObject.parseArray(config.getFieldIds(), String.class).stream().map(fieldId ->
                        headerInfoDao.findById(Integer.parseInt(fieldId))
                ).collect(Collectors.toList()),
                modelInfos);
        return configPojo;
    }

    /**
     * @param configId
     * @param confName
     * @param trainFeatureList
     * @return
     */
    public boolean changeConfig(int configId, String confName, List<String> trainFeatureList) {

        Config configInfo = configDao.findById(configId);
        configInfo.setConfName(confName);
        configInfo.setFieldIds(JSON.toJSON(trainFeatureList).toString());
        val configObject_get = configDao.save(configInfo);

        return configObject_get != null;
    }

    public boolean deleteConfigById(int configId) {

        Config configInfo = configDao.findById(configId);
        if(configInfo==null){
            return false;
        }
        List<Model> modelList = modelDao.findByConfigId(configId);
        if (modelList != null) {
            for (Model model : modelList) {
                modelDao.delete(model);
            }
        }
        configDao.delete(configDao.findById(configId));
        return true;
    }

    public List<HeaderInfo> getSelectedFeature(int fileId, String fieldName) {
        log.info("FeatureSelector: ", fileId + ", " + fieldName);
        val res = featureProject.featureSelector(fileId, fieldName);
        return res;
    }
}
