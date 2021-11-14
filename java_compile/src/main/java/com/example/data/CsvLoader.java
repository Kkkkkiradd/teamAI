package com.example.data;

import com.example.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Provide external API to load csv
 * Created by lucas on 2016/10/10.
 */
@Component
public class CsvLoader {

    private final CsvAdapter csvAdapter;

    @Autowired
    public CsvLoader(CsvAdapter csvAdapter) {
        this.csvAdapter = csvAdapter;
    }

    public int loadCsv(String filepath, int userId) throws Exception {
        try {
            String fileName = FileHelper.getFileName(filepath);
            if (fileName != null) {
                return csvAdapter.Csv2Parquet(filepath, fileName, userId);
            }
            else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
