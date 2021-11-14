package com.example.dao;

import com.example.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by LXY on 2016/11/14.
 */
@Repository
public interface ConfigDao extends JpaRepository<Config, String> {

    public Config findById(int id);
    public List<Config> findByFileInfoId(int fileInfoId);
    public  List<Config> findByUserId(int userid);

}
