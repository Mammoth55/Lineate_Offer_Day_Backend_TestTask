package com.example.offerdaysongs.model;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String songCode;

    String title;

    String version;

    ZonedDateTime releaseTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    Singer singer;

    @OneToMany(mappedBy = "recording")
    List<Copyright> copyrights;

    public Recording() {
        this.copyrights = new ArrayList<>();
    }

    public Recording(Long id, String songCode, String title, String version, ZonedDateTime releaseTime, Singer singer) {
        this.id = id;
        this.songCode = songCode;
        this.title = title;
        this.version = version;
        this.releaseTime = releaseTime;
        this.singer = singer;
        this.copyrights = new ArrayList<>();
    }
}