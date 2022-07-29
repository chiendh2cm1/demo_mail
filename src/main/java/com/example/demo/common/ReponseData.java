package com.example.demo.common;

public class ReponseData {
    protected Object data;
    protected String status;
    protected String massage;
    protected String code;
    protected String ex;

    public ReponseData() {
    }

    public ReponseData(Object data, String ex) {
        this.data = data;
        this.ex = ex;
    }

    public ReponseData(Object data, String status, String ex) {
        this.data = data;
        this.status = status;
        this.ex = ex;
    }

    public ReponseData(Object data, String status, String massage, String code, String ex) {
        this.data = data;
        this.status = status;
        this.massage = massage;
        this.code = code;
        this.ex = ex;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }
}
