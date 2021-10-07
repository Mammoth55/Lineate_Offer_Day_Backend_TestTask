package com.example.offerdaysongs.model;

import liquibase.pro.packaged.E;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String version;

    ZonedDateTime releaseTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    Singer singer;

    @OneToMany(mappedBy="recording")
    List<Copyright> copyrights;

    public Recording() {
        this.copyrights = new ArrayList<>();
    }

    public Recording(Long id, String title, String version, ZonedDateTime releaseTime, Singer singer) {
        this.id = id;
        this.title = title;
        this.version = version;
        this.releaseTime = releaseTime;
        this.singer = singer;
        this.copyrights = new ArrayList<>();
    }
}
