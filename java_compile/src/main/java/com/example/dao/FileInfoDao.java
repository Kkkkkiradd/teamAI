package com.example.dao;

import com.example.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FileInfoDao extends JpaRepository<FileInfo, String> {
    public FileInfo findById(int id);

    public List<FileInfo> findByUserId(int userId);  //find all files which belongs one user

    //find the file used the userId and fileName
    public FileInfo findByUserIdAndFilename(int userId, String filename);

}
