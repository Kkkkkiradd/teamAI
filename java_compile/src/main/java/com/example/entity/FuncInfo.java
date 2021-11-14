package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "func_info")
public class FuncInfo {
    @Id
    private int id;
    private String funcDes;
    private String funcName;
    private int flagOfType;//0--String, 1--Double, 2--all
    private int flagOfConOrDis;//0--con, 1--dis, 2--all

    public FuncInfo(String funcDes, String funcName, int flagOfType, int flagOfConOrDis) {
        this.funcName = funcName;
        this.funcDes = funcDes;
        this.flagOfType = flagOfType;
        this.flagOfConOrDis = flagOfType;
    }

    public FuncInfo(int id, int flagOfType, int flagOfConOrDis) {
        this.id = id;
        this.flagOfType = flagOfType;
        this.flagOfConOrDis = flagOfConOrDis;
    }


}
