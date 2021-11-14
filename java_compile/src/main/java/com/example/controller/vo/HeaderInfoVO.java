package com.example.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 文件头信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderInfoVO {
    private String id;
    private String fileInfoId;  //文件id
    private String fieldName;   //文件头的字段信息
    private String aliasName;   //文件字段的别名
    private String fieldDes;    //文件的描述信息
    private double nullsRatio; //field的空值比例,为百分比 如10.00%
    /**
     * conOrDis 表示了这个field的类型
     * 0表示离散值，1表示连续值，2表示时间
     * @since 1.0 记录变量为离散值和连续值
     */
    private int conOrDis;
    /**
     * fieldType 表示了这个field的类型
     * string, int, double ....
     * @since 2.0 记录变量为离散值和连续值
     */
    private String fieldType;   //值的类型
    /**
     * @since 1.0 valueInfo使用String数组存储取值，如果是离散值则为取值，连续值则存储前两位min & max
     */
    //离散值的取值或者连续值的范围
    private String[] valueInfo;

    public HeaderInfoVO(String fileInfoId, String fieldName, String fieldType) {
        this.fileInfoId = fileInfoId;
        this.fieldName = fieldName;
        this.aliasName = "";
        this.fieldDes = "";
        this.fieldType = fieldType;
        this.valueInfo = null;
        this.nullsRatio =0.0;
    }
}
