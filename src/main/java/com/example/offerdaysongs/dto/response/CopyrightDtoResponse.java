package com.example.offerdaysongs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@Schema(name = "Copyright", description = "Copyright")
public class CopyrightDtoResponse {

    private long id;

    @Schema(name = "recording", description = "recording", required = true)
    private RecordingDtoResponse recording;

    @Schema(name = "company", description = "company", required = true)
    private CompanyDtoResponse company;

    @Schema(name = "startDate", description = "Date of beginning Copyright", required = true, example = "2020-01-01")
    private ZonedDateTime startDate;

    @Schema(name = "endDate", description = "Date of ending Copyright", required = true, example = "2025-12-31")
    private ZonedDateTime endDate;

    @Schema(name = "tax", description = "Tax of Copyright", required = true, example = "99.95")
    private double tax;
}