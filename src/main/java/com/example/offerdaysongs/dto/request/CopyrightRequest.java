package com.example.offerdaysongs.dto.request;

import com.example.offerdaysongs.dto.response.CompanyDtoResponse;
import com.example.offerdaysongs.dto.response.RecordingDtoResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class CopyrightRequest {

    private RecordingDtoResponse recording;
    private CompanyDtoResponse company;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private double tax;
}