package com.ptit.quanlysinhvienthuctap.model;

import java.util.List;

public class GiangVien {
    private Integer id;
    private String name;
    private String bod;
    private Integer exp;
    private List<SinhVien> list;

    public GiangVien() {
    }

    public GiangVien(Integer id, String name, String bod, Integer exp, List<SinhVien> list) {
        this.id = id;
        this.name = name;
        this.bod = bod;
        this.exp = exp;
        this.list = list;
    }

    public GiangVien(String name, String bod, Integer exp) {
        this.name = name;
        this.bod = bod;
        this.exp = exp;
    }

    public GiangVien(Integer id, String name, String bod, Integer exp) {
        this.id = id;
        this.name = name;
        this.bod = bod;
        this.exp = exp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public List<SinhVien> getList() {
        return list;
    }

    public void setList(List<SinhVien> list) {
        this.list = list;
    }
}
