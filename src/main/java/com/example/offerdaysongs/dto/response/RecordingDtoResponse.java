package com.example.offerdaysongs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class RecordingDtoResponse {

    long id;
    String title;
    String version;
    ZonedDateTime releaseTime;
    SingerDtoResponse singer;
}
