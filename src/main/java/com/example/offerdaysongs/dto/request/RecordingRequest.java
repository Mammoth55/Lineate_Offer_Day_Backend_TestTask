package com.example.offerdaysongs.dto.request;

import com.example.offerdaysongs.model.Singer;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class RecordingRequest {

    String title;
    String version;
    ZonedDateTime releaseTime;
    Singer singer;
}
