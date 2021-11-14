package com.example.entity.inner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "model_type_argument")
public class ModelArgument {
    @Id
    private int id;
    private String name;
    private boolean isDiscrete;
    private String defaultValue;
    private String type;
    private String valueDes;
    private String description;
}
