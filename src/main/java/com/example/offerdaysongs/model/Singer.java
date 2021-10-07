package com.example.offerdaysongs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Singer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;
}
