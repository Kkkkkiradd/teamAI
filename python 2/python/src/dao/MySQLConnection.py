import pymysql


def connectdb():
    print('连接到mysql服务器...')
    # 打开数据库连接
    db = pymysql.connect(host="localhost",
                                 port=3306,
                                 user="root",
                                 passwd="1598753",
                                 db="seec_ai_cloud",
                                 charset="utf8")
    print('连接上了!')
    return db