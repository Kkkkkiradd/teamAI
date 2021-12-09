from flask import json

from src.dao import MySQLConnection
import pprint
import simplejson as json


class DLModel:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getModelById(self, modelId):
        pprint.pprint("get modelId: " + modelId)
        try:
            cursor = self.db.cursor()
            sql = "select * from dl_model where id = %s"
            cursor.execute(sql, modelId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()

    def setModelTrained(self, modelId, trained):
        try:
            cursor = self.db.cursor()
            sql = "update dl_model set trained = %s where id = %s"
            cursor.execute(sql, [trained, modelId])
            self.db.commit()
        except Exception as error:
            pprint.pprint("Update isTrained field db Error")
            raise Exception(error)
        finally:
            cursor.close()

    def setModelResult(self, modelId, acc, cost_time):
        try:
            result = {"Accuracy": str(acc), "CostTime": str(cost_time)}
            cusor = self.db.cursor()
            sql = "update dl_model set res_of_model = %s where id = %s"
            cusor.execute(sql, [json.dumps(result), modelId])
            self.db.commit()
        except Exception as error:
            pprint.pprint("Update modelResult db Error")
            raise Exception(error)
        finally:
            cusor.close()

    def setModelPath(self, modelId, path):
        try:
            cursor = self.db.cursor()
            sql = "update dl_model set model_path = %s where id = %s"
            cursor.execute(sql,[path,modelId])
            self.db.commit()
        except Exception as error:
            pprint.pprint("Update isTrained field db Error")
            raise Exception(error)
        finally:
            cursor.close()
