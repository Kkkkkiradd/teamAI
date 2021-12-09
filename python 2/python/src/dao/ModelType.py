from src.dao import MySQLConnection
import pprint


class ModelType:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getModelTypeById(self, modelTypeId):
        pprint.pprint("get modelTypeId: " + modelTypeId)
        try:
            cursor = self.db.cursor()
            sql = "select * from model_type where id = %s"
            cursor.execute(sql, modelTypeId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()

    def getModelTypeByName(self, name):
        pprint.pprint("get ModelTypeName: " + name)
        try:
            cursor = self.db.cursor()
            sql = "select * from model_type where model_type_name = %s"
            cursor.execute(sql, name)
            return cursor.fetchall()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()

