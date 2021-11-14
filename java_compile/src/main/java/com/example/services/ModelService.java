package com.example.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.controller.vo.DLModelVO;
import com.example.controller.vo.ModelVO;
import com.example.dao.*;
import com.example.data.FitTestData;
import com.example.data.LibsvmAdapter;
import com.example.entity.DLModel;
import com.example.entity.Model;
import com.example.entity.ModelType;
import com.example.entity.PicFile;
import com.example.entity.inner.ModelArgument;
import com.example.model.classification.*;
import com.example.model.cluster.KmeansCluster;
import com.example.model.helper.Utils;
import com.example.model.regression.RFRegression;
import com.example.services.pojo.DLGeneralModelPojo;
import com.example.services.pojo.DLModelPojo;
import com.example.services.pojo.ModelPojo;
import com.example.services.pojo.ModelTypePojo;
import com.example.util.Json2Object;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.spark.ml.PipelineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Config-related service
 * Created by twenbo on 2016/11/21.
 */
@Slf4j
@Data
@Component
public class ModelService {

    @Autowired
    private ConfigDao configDao;
    @Autowired
    private FileInfoDao fileInfoDao;
    @Autowired
    private PictureFileDao pictureFileDao;
    @Autowired
    private ModelDao modelDao;
    @Autowired
    private ModelTypeDao modelTypeDao;
    @Autowired
    private DLModelDao dlModelDao;
    @Autowired
    private HeaderInfoDao headerInfoDao;
    @Autowired
    private LibsvmAdapter libsvmAdapter;
    @Autowired
    private DTClassification dtClassification;
    @Autowired
    private LRClassification lrClassification;
    @Autowired
    private RFClassification rfClassification;
    @Autowired
    private GDBTClassification gdbtClassification;
    @Autowired
    private KmeansCluster kmeansCluster;

    @Autowired
    private MulPerClassification mulPerClassification;
    @Autowired
    private NaiveBayesClassification naiveBayesClassification;

    @Autowired
    private RFRegression rfRegression;

    @Autowired
    private FitTestData fitTestData;
    @Autowired
    private FileService fileService;
    @Autowired
    private Utils utils;
    /**
     *
     * @param modelVO
     * @return
     */

    public int addModel(ModelVO modelVO){

        Model modelInfo = new Model();
        modelInfo.setFileInfoId(Integer.parseInt(modelVO.getFileInfoId()));
        modelInfo.setConfigId(Integer.parseInt(modelVO.getConfigId()));
        modelInfo.setModelName(modelVO.getModelName());
        modelInfo.setModelTypeId(Integer.parseInt(modelVO.getModelTypeId()));
        modelInfo.setTrained(false);
        modelInfo.setResOfModel(null);
        modelInfo.setModelPath(null);
        modelInfo.setArguments(JSON.toJSONString(modelVO.getArguments()));
        return modelDao.save(modelInfo).getId();
    }
    public int addDlModel(DLModelVO dlModelVO) {
        DLModel dlModel = new DLModel();
        dlModel.setModelTypeId(Integer.parseInt(dlModelVO.getModelTypeId()));
        dlModel.setPicFileId(Integer.parseInt(dlModelVO.getPicFileId()));
        dlModel.setModelName(dlModelVO.getModelName());
        dlModel.setTrained(false);
        dlModel.setResOfModel(null);
        dlModel.setModelPath(null);
        dlModel.setArguments(JSON.toJSONString(dlModelVO.getArguments()));
        return dlModelDao.save(dlModel).getId();
    }
    /**
     *
     * @param userId
     * @return
     */
    public List<ModelPojo> getAllModelByUserId(int userId){

        val rsl = configDao.findByUserId(userId).stream().map((config) -> {
            int configId = config.getId();
            String configName = config.getConfName();
            //val modelAllConfig = new ArrayList<ModelPojo>();
            List modelPerConfig = modelDao.findByConfigId(configId).stream().map((model) -> {
                ModelType modelType =  modelTypeDao.findById(model.getModelTypeId());
                int flag = -1;
                if(model.isTrained() && model.getResOfModel()!=null){
                    flag = 1;
                }else if(model.isTrained() && model.getResOfModel()==null){
                    flag = 0;
                }else{
                    flag = -1;
                }
                return new ModelPojo(String.valueOf(configId),
                        configName,
                        String.valueOf(model.getModelTypeId()),
                        modelType != null ? modelType.getModelTypeName() : "",
                        modelType != null ? modelType.getModelDetailName() : "",
                        modelType != null ? modelType.getModelDetailDes() : "",
                        modelType != null ? modelType.getModelDes() : "",
                        String.valueOf(model.getId()),
                        model.getModelName(),
                        flag,
                        new Json2Object().Json2HashMap(model.getArguments()),
                        new Json2Object().Json2HashMap(model.getResOfModel()));
                }).collect(Collectors.toList());
            return modelPerConfig;
        }).reduce(new ArrayList<>(), (listA, listB) -> {
            listA.addAll(listB);
            return listA;
        });
        return rsl;
    }

    /**
     *
     * @param userId
     * @return
     */
    public List<DLGeneralModelPojo> getAllGeneralModelByUserId(int userId){
        List<PicFile> picFiles = pictureFileDao.findByUserId(userId);
        val result = picFiles.stream().map(picFile -> {
            List<DLModel> dlModels = dlModelDao.findByPicFileId(picFile.getId());
            return dlModels.stream()
                    .map(dlModel -> new DLGeneralModelPojo(String.valueOf(dlModel.getId()), dlModel.getModelName(), String.valueOf(picFile.getId()), picFile.getFileName(),dlModel.isTrained()
                            ,dlModel.getResOfModel()!=null))
                    .collect(Collectors.toList());
        }).reduce(new ArrayList<>(), (listA, listB) -> {
            listA.addAll(listB);
            return listA;
        });
        return result;
    }

    public DLModelPojo getDLModelDetailById(int modelId) {
        DLModel dlModel = dlModelDao.findById(modelId);
        return new DLModelPojo(String.valueOf(dlModel.getModelTypeId()),String.valueOf(dlModel.getPicFileId()),
                dlModel.getModelName(),dlModel.isTrained(),dlModel.getModelPath(),new Json2Object().Json2HashMap(dlModel.getResOfModel())
        ,new Json2Object().Json2HashMap(dlModel.getArguments()));
    }

    /**
     *
     * @param modelTypeName
     * @return
     */
    public List<ModelType> getModelTypeByModelTypeName(String modelTypeName) {
        return modelTypeDao.findByModelTypeName(modelTypeName);
    }

    /**
     *
     * @param modelId
     * @param modelName
     * @param modelTypeId
     * @return
     */
    public boolean changeModel(int modelId, String modelName, int modelTypeId){

        Model model = modelDao.findById(modelId);
        model.setModelName(modelName);
        model.setModelTypeId(modelTypeId);
        val modelObject = modelDao.save(model);
        if(modelObject!=null){
            return true;
        }else {
            return false;
        }
    }

    /**
     *change status of isTrained
     * @param modelId
     * @return
     */
    public boolean changeStatusOfTrained(int modelId, boolean trained){

        Model model =  modelDao.findById(modelId);
        model.setTrained(trained);
        val modelObject = modelDao.save(model);
        return modelObject != null;
    }

    /**
     *
     * @param modelId
     * @param resOfModel
     * @param model
     * @return
     */
    public boolean updataResOfModel(int modelId, HashMap<String, String> resOfModel, Object model){
        Model modelOfGet =  modelDao.findById(modelId);
        //T.B.D.
        //modelOfGet.setModel(model);
        modelOfGet.setResOfModel(JSON.toJSONString(resOfModel));
        modelOfGet.setTrained(true);

        val modelObject = modelDao.save(modelOfGet);
        if(modelObject!=null){
            return true;
        }else {
            return false;
        }
    }



    public boolean trainTheModel(int modelId) throws Exception{
        //change training flag to true


        changeStatusOfTrained(modelId, true);

        val trainingModel = modelDao.findById(modelId);
        val modelType1 = modelTypeDao.findById(trainingModel.getModelTypeId());
        val configInfo = configDao.findById(trainingModel.getConfigId());
        val features = JSONObject.parseArray(configInfo.getFieldIds(),String.class).stream().map(fieldId ->
                headerInfoDao.findById(Integer.parseInt(fieldId)).getFieldName()
        ).collect(Collectors.toList());

        //1. transfer file to libsvm
        log.info("Transfer to LibSVM...");
        String keyFeature = features.get(features.size()-1);
        features.remove(features.size()-1);
        val featureNum = features.size();
        String libsvm = libsvmAdapter.parquet2Libsvm(configInfo.getFileInfoId(), keyFeature, features.toArray(new String[features.size()]));

        String modelDetailName = modelType1.getModelDetailName();
        val modelArguments = new Json2Object().Json2HashMap(trainingModel.getArguments());
        boolean rsl = false;
        try {
            switch (modelDetailName) {
                case "LogisticRegression":
            /*
            * maxIter: Int,
                 regParam: Float,
                 elasticNetParam: Float
            * */
                    val lrModel = lrClassification.lrTraining(libsvm,
                            Integer.parseInt(modelArguments.get("MaxIter")),
                            Float.parseFloat(modelArguments.get("RegParam")),
                            Float.parseFloat(modelArguments.get("ElasticNetParam")));
                    val lrRst = lrClassification.lrResult(lrModel);

                    rsl = updataResOfModel(modelId, lrRst, lrModel);
                    break;
                case "DecisionTree":
            /*
            * maxIter: Int,
                 regParam: Float,
                 elasticNetParam: Float
            * */
                    val dtModel = dtClassification.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingSetOccupy")));
                    val dtResult = dtClassification.dtResult(dtModel, modelId);

                    rsl = updataResOfModel(modelId, dtResult, dtModel);
                    break;
                case "RandomForest":
                    /*
                * maxIter: Int,
                 regParam: Float,
                 elasticNetParam: Float
            * */

                    val rfModel = rfClassification.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingDataSetOccupy")));
                    val rfResult = rfClassification.dtResult(rfModel, modelId);

                    rsl = updataResOfModel(modelId,rfResult,rfModel);
                    break;
                case "GBDT":
                    /*
                * maxIter: Int,
                 regParam: Float,
                 elasticNetParam: Float
            * */

                    val gbtModel = gdbtClassification.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingDataSetOccupy")));
                    val gbtResult = gdbtClassification.dtResult(gbtModel, modelId);

                    rsl = updataResOfModel(modelId,gbtResult,gbtModel);
                    break;
                case "K-Means":

                    val kmeansModel = kmeansCluster.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("NumOfCluster")),
                            Integer.parseInt(modelArguments.get("Seed")));
                    val kmeansResult = kmeansCluster.dtResult(kmeansModel);
                    rsl = updataResOfModel(modelId,kmeansResult,kmeansModel);
                    break;
                case "MultilayerPerceptronClassifier":

                    val mlpc = mulPerClassification.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingDataSetOccupy")), featureNum);
                    val mlpcResult = mulPerClassification.dtResult(mlpc, modelId);

                    rsl = updataResOfModel(modelId,mlpcResult,mlpc);
                    break;
                case "NaiveBayes":

                    val nby = naiveBayesClassification.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingDataSetOccupy")));
                    val nbyResult = naiveBayesClassification.dtResult(nby, modelId);

                    rsl = updataResOfModel(modelId,nbyResult,nby);
                    break;

                case "RandomForestRegression":

                    val rfReg = rfRegression.dtTraining(libsvm,
                            Integer.parseInt(modelArguments.get("Category")),
                            Float.parseFloat(modelArguments.get("TrainingDataSetOccupy"))
                            );
                    val rfRegResult = rfRegression.dtResult(rfReg);

                    rsl = updataResOfModel(modelId,rfRegResult,rfReg);

                    break;
                default:
                    rsl = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            changeStatusOfTrained(modelId, false);
        }
        if(!rsl){
            changeStatusOfTrained(modelId,false);
        }
        return rsl;
    }

    /**
     *
     * @param modelId
     */

    public boolean deleteModel(int modelId) {
        Model model =  modelDao.findById(modelId);
        if(model == null){
            return false;
        }
        modelDao.delete(modelDao.findById(modelId));
        return true;

    }
    public List<Map<String, String>> useModel(int fileId, int modelId) throws Exception{
        val res = fitTestData.transformData(fileId,modelId);
        fileService.deleteFile(fileId);
        return res;
    }


}
