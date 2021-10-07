package com.example.offerdaysongs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class CopyrightDtoResponse {

    private long id;
    private RecordingDtoResponse recording;
    private CompanyDtoResponse company;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private double tax;
}