package com.example.demo.common;

public class ResponseDataPagination extends ReponseData{
private Object pagination;

    public ResponseDataPagination(Object pagination) {
        this.pagination = pagination;
    }

    public ResponseDataPagination(Object data, String ex, Object pagination) {
        super(data, ex);
        this.pagination = pagination;
    }

    public ResponseDataPagination(Object data, String status, String ex, Object pagination) {
        super(data, status, ex);
        this.pagination = pagination;
    }

    public ResponseDataPagination() {
    }

    public Object getPagination() {
        return pagination;
    }

    public void setPagination(Object pagination) {
        this.pagination = pagination;
    }
}
