package com.example.entity.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Keras Layer Arguments
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "layer_argument")
public class LayerArgument {
    @Id
    private int id;
    private boolean isDiscrete;  //是否为离散值
    private String value; //参数的默认值
    private String type;         //模型参数取值为Float|Int|...
    private String[] valueDes;   //取值范围或者离散值则为取值量
    private String description;  //对取值或者参数的说明
}
