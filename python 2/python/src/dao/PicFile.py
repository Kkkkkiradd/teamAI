from src.dao import MySQLConnection
import pprint


class PicFile:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getPicFileById(self, picId):
        pprint.pprint("get picId: " + picId)
        try:
            cursor = self.db.cursor()
            sql = "select * from pic_file where id = %s"
            cursor.execute(sql, picId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()
