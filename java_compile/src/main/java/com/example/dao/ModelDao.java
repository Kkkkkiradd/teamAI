package com.example.dao;

import com.example.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelDao extends JpaRepository<Model, String> {

    public List<Model> findByFileInfoId(int fileInfoId);
    public List<Model> findByConfigId(int configId);
    public Model findById(int modelId);

    @Override
    void delete(Model model);
}
