package com.example.offerdaysongs.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordingRequest {

    String songCode;
    String title;
    String version;
    String releaseTime;
    SingerRequest singer;
}