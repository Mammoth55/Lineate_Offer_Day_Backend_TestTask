package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<Copyright> copyrights;

    public Company() {
        this.copyrights = new ArrayList<>();
    }

    public Company(long id, String name) {
        this.id = id;
        this.name = name;
        this.copyrights = new ArrayList<>();
    }
}