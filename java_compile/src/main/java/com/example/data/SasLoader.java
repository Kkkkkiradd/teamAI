package com.example.data;

import com.example.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provide external API to load sas7bdat
 * Created by lucas on 2016/10/10.
 */
@Component
public class SasLoader {

    private final SasAdapter sasAdapter;

    @Autowired
    public SasLoader(SasAdapter sasAdapter) {
        this.sasAdapter = sasAdapter;
    }

    public int loadSas(String filepath, int userId) throws Exception {
        try {
            String fileName = FileHelper.getFileName(filepath);
            if(fileName!=null){
                return sasAdapter.Sas2Parquet(filepath,fileName,userId);
            }else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
