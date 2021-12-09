from src.dao import MySQLConnection
import pprint
import json


class Model:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getModelById(self, modelId):
        pprint.pprint("get modelId: " + modelId)
        try:
            cursor = self.db.cursor()
            sql = "select * from model where id = %s"
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
            sql = "update model set trained = %s where id = %s"
            cursor.execute(sql, [trained,modelId])
            self.db.commit()
        except Exception as error:
            pprint.pprint("Update isTrained field db Error")
            raise Exception(error)
        finally:
            cursor.close()

    def setModelResult(self, modelId, acc, other_key='Loss', other_value=1):
        try:
            result = {other_key: other_value, 'Accuracy': acc}
            cusor = self.db.cursor()
            sql = "update model set res_of_model = %s where id = %s"
            cusor.execute(sql, [json.dumps(result), modelId])
            self.db.commit()
            # self.dao.update_one({"_id": ObjectId(modelId)}, {'$set': {'resOfModel': result}}, upsert=False)
        except Exception as error:
            pprint.pprint("Update modelResult db Error")
            raise Exception(error)
        finally:
            cusor.close()
