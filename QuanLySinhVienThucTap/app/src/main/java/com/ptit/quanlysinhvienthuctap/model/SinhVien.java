package com.ptit.quanlysinhvienthuctap.model;

public class SinhVien {
    private Integer id;
    private String name;
    private String bod;
    private String address;
    private Integer teacherId;

    public SinhVien() {
    }

    public SinhVien(Integer id, String name, String bod, String address, Integer teacherId) {
        this.id = id;
        this.name = name;
        this.bod = bod;
        this.address = address;
        this.teacherId = teacherId;
    }

    public SinhVien(Integer id, String name, String bod, String address) {
        this.id = id;
        this.name = name;
        this.bod = bod;
        this.address = address;
    }
    public SinhVien( String name, String bod, String address, Integer teacherId) {
        this.name = name;
        this.bod = bod;
        this.address = address;
        this.teacherId = teacherId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }
}
