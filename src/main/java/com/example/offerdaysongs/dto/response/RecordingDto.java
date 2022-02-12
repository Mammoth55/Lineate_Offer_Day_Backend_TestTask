package com.example.offerdaysongs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RecordingDtoResponse", description = "Recording Info")
public class RecordingDto {

    long id;

    @Schema(name = "songCode", description = "Recording type", required = true, example = "Rock")
    String songCode;

    @Schema(name = "title", description = "Recording title", required = true, example = "Bohemian Rapsody")
    String title;

    @Schema(name = "version", description = "Recording version", required = true, example = "Original")
    String version;

    @Schema(name = "releaseTime", description = "Date of Recording release", required = true, example = "1984-06-06")
    String releaseTime;

    @Schema(name = "singer", description = "singer", required = true)
    SingerDtoResponse singerDto;
}
