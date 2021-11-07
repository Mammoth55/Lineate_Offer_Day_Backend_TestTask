package com.example.offerdaysongs.dto.request;

import lombok.Data;

@Data
public class CompanyRequest {

    private String name;

    public CompanyRequest(String name) {
        this.name = name;
    }
}