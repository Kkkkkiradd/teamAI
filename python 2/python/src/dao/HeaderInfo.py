from src.dao import MySQLConnection
import pprint


class HeaderInfo:
    def __init__(self):
        self.db = MySQLConnection.connectdb()

    def getByFileIdAndFieldName(self, fileId, fieldName):
        pprint.pprint("fileId: " + fileId + " fieldName: " + fieldName)
        try:
            cursor = self.db.cursor()
            sql = "select * from header_info where file_info_id = %s and field_name = %s"
            cursor.execute(sql,[fileId,fieldName])
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()

    def getHeaderInfoById(self, fieldId):
        pprint.pprint("get FieldId: " + fieldId)
        try:
            cursor = self.db.cursor()
            sql = "select * from header_info where id = %s"
            cursor.execute(sql, fieldId)
            return cursor.fetchone()
        except Exception as error:
            pprint.pprint("DB Error")
            raise Exception(error)
        finally:
            cursor.close()
