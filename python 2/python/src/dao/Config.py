import pprint
from src.dao import MySQLConnection


class Config:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getConfigById(self, configId):
        pprint.pprint("get configId: " + configId)
        try:
            cursor = self.db.cursor()
            sql = "select * from config where id = %s"
            cursor.execute(sql,configId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()
