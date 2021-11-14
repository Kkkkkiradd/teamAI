package com.example.dao;

import com.example.entity.DLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DLModel mongo repository
 * Created by lucas on 2017/6/18.
 */
@Repository
public interface DLModelDao extends JpaRepository<DLModel, String> {

    public List<DLModel> findByPicFileId(int picFileId);

    public DLModel findById(int modelId);
}
