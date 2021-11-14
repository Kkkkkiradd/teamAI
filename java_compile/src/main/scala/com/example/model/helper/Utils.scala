package com.example.model.helper

import java.io.{ByteArrayOutputStream, ObjectOutputStream}

import com.example.SparkConnect
import com.example.dao.{HdfsDao, ModelDao}
import org.apache.spark.ml.PipelineModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
  * Created by twb on 2017/5/3.
  */
@Component
class Utils extends SparkConnect{

  @Autowired
  val hdfsDao: HdfsDao = null

  @Autowired
  val modelDao: ModelDao = null


  @throws(classOf[Exception])
  def storeModel(pipelineModel: PipelineModel, modelId: Int): Boolean = {
    val modelPath = hdfsServer  + "_" + modelId + ".model"


    hdfsDao.deleteFileInHdfs(modelPath, true)

    val flag = pipelineModel.save(modelPath)
    val modelOfGet = modelDao.findById(modelId)

    modelOfGet.setModelPath(modelPath)

    val modelObject = modelDao.save(modelOfGet)
    if (modelObject != null) return true

    return false
//    val os = new ByteArrayOutputStream() // 定义一个字节数组输出流
//    val out = new ObjectOutputStream(os) // 对象输出流
//    out.writeObject(pipelineModel)
//    val modelByte = os.toByteArray // byte[]
//    var modelFile = new ModelFile() // 申明一个模型对象
//    modelFile.setModel(modelByte)
//    os.close()
//    out.close()
//    val modelFileId = modelFileDao.save(modelFile).getId
//    val modelOfGet = modelDao.findById(modelId)
//    modelOfGet.setModelPath(modelFileId.toString)
//    val modelObject = modelDao.save(modelOfGet)
//    if (modelObject != null) return true
//    return false
  }
}
