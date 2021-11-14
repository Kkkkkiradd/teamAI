package com.example.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author cyz
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "pic_category")
public class PicCategory {

    @Id
    private int id;

    private int picFileId;

    private String categoryName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId(){
        return id;
    }

    public PicCategory(String categoryName, int picFileId) {
        this.picFileId = picFileId;
        this.categoryName = categoryName;

    }

}
