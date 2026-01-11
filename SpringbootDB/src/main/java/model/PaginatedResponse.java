package com.example.bankingsystemsb;

import java.util.List;

public class PaginatedResponse<T> { // T is a placeHolder for a type stored later
    private int page;
    private int size;
    private int totalUsers;
    private List<T> data;

    public PaginatedResponse() {

    }
    public PaginatedResponse(int page, int size, int totalUsers, List<T> data) {
        this.page = page;
        this.size = size;
        this.totalUsers = totalUsers;
        this.data = data;
    }


    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getTotalUsers() {
        return totalUsers;
    }

    public List<T> getData() {
        return data;
    }



}

