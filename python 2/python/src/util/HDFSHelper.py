import configparser


class HDFSHelper:
    def __init__(self):
        config = configparser.ConfigParser()
        config.read("../config.ini")
        master = config.get('SPARK', 'HDFSAddr')
        self.master = master
    def saveToHDFS(self, model, modelName):
        path =self.master+ modelName + '.h5'
        model.save(path)
        return path