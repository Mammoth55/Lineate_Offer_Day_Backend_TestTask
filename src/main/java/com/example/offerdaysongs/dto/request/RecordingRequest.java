package com.example.offerdaysongs.dto.request;

import com.example.offerdaysongs.model.Singer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class RecordingRequest {

    String songCode;
    String title;
    String version;
    ZonedDateTime releaseTime;
    Singer singer;
}
