package com.example.controller;

import com.example.controller.response.ErrorRes;
import com.example.controller.response.NormalRes;
import com.example.controller.response.Response;
import com.example.data.CsvAdapter;
import com.example.services.FileService;
import com.example.services.pojo.HeaderInfoUpdatedPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * File uploader and transfer and so on API
 * Created by lucas on 2016/10/9.
 */
@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    @Autowired private   FileService fileService;
    @Autowired private CsvAdapter csvAdapter;
    /**
     *
     * @param file
     * @return fileId
     */
    @PostMapping(value = "/upload")
    public Response fileLoader(@RequestParam(value="userId", required = false) String userId,
                               @RequestParam(value="file", required = false) MultipartFile file,
                               @RequestParam(value="fileName") String filename) {
        try {
            int rsl = fileService.uploadFile(Integer.parseInt(userId), file, filename);
            return rsl != 0 ? new NormalRes(rsl) : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            log.info("error fileLoader");
            return new ErrorRes();
        }
    }
    @GetMapping(value = "/upload/bigfile")
    public Response bigFileUploader(@RequestParam(value="userId", required = false) String userId,
                                    @RequestParam(value="fileLoc", required = false) String fileLoc,
                                    @RequestParam(value="fileName", required = false) String fileName) {

        try {
            int rsl = csvAdapter.Csv2Parquet(fileLoc, fileName, Integer.parseInt(userId));
            return rsl != 0 ? new NormalRes(rsl) : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            log.info("error bigFileUploader");
            return new ErrorRes();
        }
    }

    @GetMapping(value = "/get/all")
    public Response getAll(@RequestParam(value="userId", required = false) String userId) {
        try {
            System.out.println(userId);
            return new NormalRes(fileService.getAllFiles(Integer.parseInt(userId)));
        } catch (Exception e) {
            log.info("error getAll");
            return new ErrorRes();
        }
    }
    @GetMapping(value = "/get/allfileandfunc")
    public Response getAllFileAndFunc(@RequestParam(value="userId", required = false)String userId){
        try {

            return new NormalRes(fileService.getAllFilesAndFunc(Integer.parseInt(userId)));
        } catch (Exception e) {
            log.info("error getAllFileAndFunc");
            return new ErrorRes();
        }
    }


    @GetMapping(value = "/get/detail")
    public Response getDetail(@RequestParam(value="fileId", required = false) String fileId){
        try{
            return new NormalRes(fileService.getFileDetailInfo(Integer.parseInt(fileId)));
        } catch (Exception e){
            log.info("error getDetail");
            return new ErrorRes();
        }
    }
    @GetMapping(value = "/analysis")
    public Response analysis(@RequestParam(value="fileId", required = false) String fileId){
        try{
            fileService.analysis(Integer.parseInt(fileId));
            return new NormalRes(fileService.getFileDetailInfo(Integer.parseInt(fileId)));
        } catch (Exception e){
            log.info("error analysis");
            return new ErrorRes();
        }
    }

    /**
     * for test 数据分布
     * @param fieldIds
     * @return
     */
    @GetMapping(value = "/analysis/fields1")
    public Response analysisOfFields(@RequestParam(value="fieldIds", required = false) String[] fieldIds){
        try{
            return new NormalRes(fileService.getFieldDistribution(fieldIds));
        } catch (Exception e){
            log.info("error analysisOfFields");
            return new ErrorRes();
        }
    }

    /**
     *
     * @param fieldIds
     * @param funcId
     * @return
     */
    @GetMapping(value = "/analysis/fields")
    public Response analysisOfFields1(@RequestParam(value = "fieldIds", required = false) String[] fieldIds,
                                      @RequestParam(value = "funcId", required = false) String funcId){
        try{
            return new NormalRes(fileService.getFieldAnalysis(fieldIds, Integer.parseInt(funcId)));
        } catch (Exception e){
            log.info("error analysisOfFields1");
            return new ErrorRes();
        }
    }


    ///**
    // *
    // * @param nums
    // * @return
    // */
    //@GetMapping(value = "/analysis/test")
    //public Response analysis2(@RequestParam(value="nums", required = false) int nums){
    //    try{
    //        fileService.analysisTest(nums);
    //        return new NormalRes();
    //    } catch (Exception e){
    //        log.info("error analysis2");
    //        return new ErrorRes();
    //    }
    //}

    /**
     *
     * @param fileId
     * @return
     */
    @GetMapping(value = "/delete")
    public Response delete(@RequestParam(value="fileId", required = false) String fileId){
        try{
            return new NormalRes(fileService.deleteFileById(Integer.parseInt(fileId)));
        } catch (Exception e){
            log.info("error delete");
            return new ErrorRes();
        }
    }

    /**
     *
     * @param headerInfoUpdatedPojo
     * @return
     */
    @PostMapping(value = "update")
    public Response updateFileInfo(
            @RequestBody HeaderInfoUpdatedPojo headerInfoUpdatedPojo
    ) {
        try {
            boolean rsl = fileService.updateAllHeaders(headerInfoUpdatedPojo.getHeaderInfo(),headerInfoUpdatedPojo.getHeaderNeedUpdate());
            return rsl? new NormalRes() : new ErrorRes(40002, "Server error.");
        } catch (Exception e) {
            log.info("error updateFileInfo");
            return new ErrorRes();
        }

    }
}
