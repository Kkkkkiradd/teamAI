package com.example.model.classification

import java.util

import com.example.SparkConnect
import com.example.dao.HdfsDao
import com.example.model.helper.Utils
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.{GBTClassificationModel, GBTClassifier}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.sql.{Dataset, Row}
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
/**
  * Created by tangwb on 2017/3/24.
  * 梯度下降决策树
  */
@Component
class GDBTClassification extends SparkConnect{

  var trainingSet:Dataset[Row]  = _
  var testData:Dataset[Row] = _
  @Autowired
  val hdfsDao: HdfsDao = null

  @Autowired
  val utils: Utils = null
  /**
    *
    * @param libsvmFile
    * @param category
    * @param trainingSetOccupy
    * @throws java.lang.Exception
    * @return gdbt model{PipelineModel}
    */

  @throws(classOf[Exception])
  def dtTraining(libsvmFile: String,
                 category: Int,
                 trainingSetOccupy: Float
                ): PipelineModel ={
    // Load the data stored in LIBSVM format as a DataFrame.
    val data = sparkSession.read.format("libsvm").load(libsvmFile)
    // Index labels, adding metadata to the label column.
    // Fit on whole dataset to include all labels in index.
    val labelIndexer = new StringIndexer()
      .setInputCol("label")
      .setOutputCol("indexedLabel")
      .fit(data)
    // Automatically identify categorical features, and index them.
    val featureIndexer = new VectorIndexer()
      .setInputCol("features")
      .setOutputCol("indexedFeatures")
      .setMaxCategories(category) // features with > 4 distinct values are treated as continuous.
      .fit(data)
    // Split the data into training and test sets (30% held out for testing).
    val Array(trainingSet, testSet) = data.randomSplit(Array(trainingSetOccupy, 1 - trainingSetOccupy))
    testData = testSet

    // Train a gdbt model.
    val gbt = new GBTClassifier().setLabelCol("indexedLabel")
      .setFeaturesCol("indexedFeatures")

    // Convert indexed labels back to original labels.
    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)

    // Chain indexers and tree in a Pipeline.
    val pipeline = new Pipeline()
      .setStages(Array(labelIndexer, featureIndexer, gbt, labelConverter))

    // Train model. This also runs the indexers.
    val model = pipeline.fit(trainingSet)

    model

  }

  /**
    *
    * @param pipelineModel
    * @throws java.lang.Exception
    * @return
    */

  @throws(classOf[Exception])
  def dtResult(pipelineModel: PipelineModel, modelId: Int): util.HashMap[String, String] = {
    val rslMap = new util.HashMap[String, String]()
    // Make predictions.
    val predictions = pipelineModel.transform(testData)




    // Select example rows to display.
    val matrixs = predictions.select("predictedLabel", "label")
    matrixs.show()
    matrixs.createOrReplaceTempView("Matrixs")
    println("12121"+matrixs.dtypes)
    // Select (prediction, true label) and compute test error.
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("indexedLabel")
      .setPredictionCol("prediction")
    //.setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println("Test Error = " + (1.0 - accuracy))
    val treeModel = pipelineModel.stages(2).asInstanceOf[GBTClassificationModel]

    println("Learned classification tree model:\n" + treeModel.toDebugString)
    val weightedRecall = evaluator.setMetricName("weightedRecall").evaluate(predictions)
    val weightedPrecision = evaluator.setMetricName("weightedPrecision").evaluate(predictions);

    val f1 = evaluator.setMetricName("f1").evaluate(predictions);
    rslMap.put("Accuracy: ", accuracy.toString)
    rslMap.put("weightedPrecision", weightedPrecision.toString)
    rslMap.put("weightedRecall", weightedRecall.toString)
    rslMap.put("f1", f1.toString)

    if(utils.storeModel(pipelineModel, modelId))
      rslMap
    return null
  }

}
