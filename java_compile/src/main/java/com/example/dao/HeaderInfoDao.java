package com.example.dao;

import com.example.entity.HeaderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface HeaderInfoDao extends JpaRepository<HeaderInfo, String> {
    public HeaderInfo findById(int id);
    public List<HeaderInfo> findByFileInfoId(int fileInfoId);
    public List<HeaderInfo> findByFieldNameAndFileInfoId(String fieldName, int fileInfoId);
}
