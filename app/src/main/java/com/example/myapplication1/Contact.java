package com.example.myapplication1;

public class Contact {
    private  int id;
    private String name;
    private String sdt;
    private  int isCheck;

    public Contact(int id, String name, String sdt, int isCheck) {
        this.id = id;
        this.name = name;
        this.sdt = sdt;
        this.isCheck = isCheck;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public int isCheck() {
        return isCheck;
    }

    public void setCheck(int check) {
        isCheck = check;
    }
}
