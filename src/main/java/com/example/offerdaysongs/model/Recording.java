package com.example.offerdaysongs.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recording {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songCode;

    private String title;

    private String version;

    private ZonedDateTime releaseTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Singer singer;

    @OneToMany(mappedBy = "recording")
    private List<Copyright> copyrights;
}