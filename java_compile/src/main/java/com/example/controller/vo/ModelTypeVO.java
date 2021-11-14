package com.example.controller.vo;

import com.example.entity.inner.ModelArgument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelTypeVO {
    private String id;

    private String modelTypeName;

    private String modelDetailName;

    private Set<ModelArgument> arguments = new HashSet<>();

    private String modelDetailDes;

    private String modelDes;
}
