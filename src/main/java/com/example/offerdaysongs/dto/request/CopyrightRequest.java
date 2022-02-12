package com.example.offerdaysongs.dto.request;

import com.example.offerdaysongs.dto.response.CompanyDto;
import com.example.offerdaysongs.dto.response.RecordingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CopyrightRequest {

    private RecordingDto recording;

    private CompanyDto company;

    private String startDate;

    private String endDate;

    private double tax;
}