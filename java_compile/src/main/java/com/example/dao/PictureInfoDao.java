package com.example.dao;

import com.example.entity.PicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FileInfo mongo auto dao
 *
 */
@Repository
public interface PictureInfoDao extends JpaRepository<PicInfo, String> {

    public PicInfo findById(int id);

    public List<PicInfo> findByCategoryId(int categoryId);

}
