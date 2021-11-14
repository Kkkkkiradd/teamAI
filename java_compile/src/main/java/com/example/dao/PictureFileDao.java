package com.example.dao;

import com.example.entity.PicFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * FileInfo mongo auto dao
 *
 */
@Repository
public interface PictureFileDao extends JpaRepository<PicFile, String> {

    public PicFile findById(int id);

    public List<PicFile> findByUserId(int userId);  //find all files which belongs one user



}
