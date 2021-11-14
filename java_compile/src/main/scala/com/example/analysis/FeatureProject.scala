package com.example.analysis

import java.util

import com.example.SparkConnect
import com.example.dao.{FileInfoDao, HeaderInfoDao}
import com.example.data.LibsvmAdapter
import com.example.entity.HeaderInfo
import org.apache.spark.ml.feature.ChiSqSelector
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.collection.JavaConversions._
import scala.util.control.Exception

/**
 * The feature project object
 * Created by lucas on 2017/4/7.
 */
@Component
class FeatureProject extends SparkConnect{
  @Autowired val fileInfoDao: FileInfoDao = null
  @Autowired val headerInfoDao: HeaderInfoDao = null
  @Autowired val libsvmAdapter: LibsvmAdapter = null

  /**
   *
   * @param fileId
   * @param fieldName
   * @throws Exception
   * @author lucas
   */
  def featureSelector(fileId: Int, fieldName: String): util.ArrayList[HeaderInfo] = {
    val headerInfos = headerInfoDao.findByFileInfoId(fileId).toList.map(headerInfo => {
      if(headerInfo.getNullsRatio == 100) null
      else headerInfo.getFieldName
    }).filter(name => { name != null && !name.equals(fieldName) })

    log.info("header_infos: " + headerInfos)
    val svmLoc = libsvmAdapter.parquet2Libsvm(fileId, fieldName, headerInfos.toArray[String])

    val svmDF = sparkSession.read.format("libsvm").load(svmLoc).toDF("id", "features")
    svmDF.show()

    val num = (headerInfos.size * 0.2).toInt

    val selector = new ChiSqSelector()
      .setNumTopFeatures(num)
      .setFeaturesCol("features")
      .setLabelCol("id")
      .setOutputCol("selectedFeatures")

    val result = selector.fit(svmDF).selectedFeatures
    val res = new util.ArrayList[HeaderInfo]()
    for (i <- result) {
      res.add(headerInfoDao.findByFieldNameAndFileInfoId(headerInfos.get(i),fileId).head)
    }
    res
  }
}
