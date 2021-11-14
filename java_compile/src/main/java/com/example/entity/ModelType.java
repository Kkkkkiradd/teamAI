package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "model_type")
public class ModelType {

    @Id
    private int id;

    private String modelTypeName;

    private String modelDetailName;

    private String arguments;

    private String modelDetailDes;

    private String modelDes;

    public ModelType(String modelTypeName, String modelDetailName, String arguments,
                     String modelDetailDes, String modelDes) {
        this.modelTypeName = modelTypeName;
        this.modelDetailName = modelDetailName;
        this.arguments = arguments;
        this.modelDetailDes = modelDetailDes;
        this.modelDes = modelDes;
    }

}
