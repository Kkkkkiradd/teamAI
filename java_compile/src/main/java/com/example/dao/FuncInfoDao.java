package com.example.dao;


import com.example.entity.FuncInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FuncInfoDao extends JpaRepository<FuncInfo, String> {

    public FuncInfo findById(int Id);

}
