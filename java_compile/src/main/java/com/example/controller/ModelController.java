package com.example.controller;


import com.example.controller.response.ErrorRes;
import com.example.controller.response.NormalRes;
import com.example.controller.response.Response;
import com.example.controller.vo.DLModelVO;
import com.example.controller.vo.ModelVO;
import com.example.services.FileService;
import com.example.services.ModelService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Model-related controller
 * Created by twenbo on 2016/11/21.
 */
@Slf4j
@RestController
@RequestMapping("/api/model")
public class ModelController {

    @Autowired
    private ModelService modelService;
    @Autowired
    private FileService fileService;


    /**
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/add")
    public Response addModel(@RequestBody ModelVO model){
        try {
            int rsl = modelService.addModel(model);
            return rsl != 0? new NormalRes(rsl) : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/get/all")
    public Response getAllModel(@RequestParam(value="userId", required = false) String userId) {
        try{
            return new NormalRes(modelService.getAllModelByUserId(Integer.parseInt(userId)));
        }catch (Exception e)
        {
           e.printStackTrace();
            return new ErrorRes();
        }
    }

    /**
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/get/all/dl")
    public Response getAllDLModel(@RequestParam(value="userId", required = false) String userId) {
        try{
            return new NormalRes(modelService.getAllGeneralModelByUserId(Integer.parseInt(userId)));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    @GetMapping(value = "/get/detail/dl")
    public Response getDLDetailModel(@RequestParam(value="modelId", required = false) String modelId) {
        try{
            return new NormalRes(modelService.getDLModelDetailById(Integer.parseInt(modelId)));
        }catch (Exception e)
        {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    /**
     *
     * @param model
     * @return
     */
    @PostMapping(value = "/add/dl")
    public Response addDLModel(@RequestBody DLModelVO model){
        try {
            int rsl = modelService.addDlModel(model);
            return rsl != 0? new NormalRes(rsl) : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }


    /**
     *
     * @param modelId
     * @param modelName
     * @param modelTypeId
     * @return
     */
    @GetMapping(value = "/change")
    public Response changeModel(@RequestParam(value="modelId", required = false) String modelId,
                                 @RequestParam(value="modelName") String modelName,
                                 @RequestParam(value="modelTypeId") String modelTypeId) {
        try{
            boolean rsl = modelService.changeModel(Integer.parseInt(modelId), modelName, Integer.parseInt(modelTypeId));
            return rsl? new NormalRes() : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    @GetMapping(value = "/get/models")
    public Response getModelType(@RequestParam(value="modelTypeName", required = false) String modelTypeName) {
        try {
            return new NormalRes(modelService.getModelTypeByModelTypeName(modelTypeName));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    @GetMapping(value="/train")
    public Response trainModel(@RequestParam(value="modelId", required = false) String modelId) {
        try {
            return new NormalRes(modelService.trainTheModel(Integer.parseInt(modelId)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    @GetMapping(value="/delete")
    public Response deleteModelByModelId(@RequestParam(value="modelId", required = false) String modelId){
        try {
            return new NormalRes(modelService.deleteModel(Integer.parseInt(modelId)));
        }catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }

    @PostMapping(value="/use/upload")
    public Response uploadTestFile(@RequestParam(value="userId", required = false) String userId,
                                   @RequestParam(value="file", required = false) MultipartFile file,
                                   @RequestParam(value="fileName") String filename) {
        try {
            val rsl = fileService.uploadTestFile(Integer.parseInt(userId), file, filename);
            return rsl != null ? new NormalRes(rsl) : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }
    @GetMapping(value="/use")
    public Response useModel(@RequestParam(value = "fileId", required = false) String fileId,
                             @RequestParam(value = "modelId", required = false) String modelId) {
        try {
                return new NormalRes(modelService.useModel(Integer.parseInt(fileId), Integer.parseInt(modelId)));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorRes();
        }
    }


}
