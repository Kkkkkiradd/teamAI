package com.example.dao;

import com.example.entity.ModelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ModelTypeDao  extends JpaRepository<ModelType, String> {

    public ModelType findById(int Id);

    public List<ModelType> findByModelTypeName(String modelTypeName);


    public ModelType findByModelTypeNameAndModelDetailName(String modelTypeName, String specialModelName);
}
