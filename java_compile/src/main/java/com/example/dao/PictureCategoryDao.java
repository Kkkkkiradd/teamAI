package com.example.dao;

import com.example.entity.PicCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FileInfo mongo auto dao
 *
 */
@Repository
public interface PictureCategoryDao extends JpaRepository<PicCategory, String> {

    public PicCategory findById(int id);

    public List<PicCategory> findByPicFileId(int picFileId);



}
