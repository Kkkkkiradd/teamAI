from src.dao import MySQLConnection
import pprint


class FileInfo:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getFileInfoById(self, fileId):
        pprint.pprint("get fileId: " + fileId)
        try:
            cursor = self.db.cursor()
            sql = "select * from file_info where id = %s"
            cursor.execute(sql,fileId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()