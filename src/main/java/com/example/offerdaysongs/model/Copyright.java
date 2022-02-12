package com.example.offerdaysongs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Copyright {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "recording_id", nullable = false)
    private Recording recording;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private ZonedDateTime startDate;

    private ZonedDateTime endDate;

    private double tax;
}