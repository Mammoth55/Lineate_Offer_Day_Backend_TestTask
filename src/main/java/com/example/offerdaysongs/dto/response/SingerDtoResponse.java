package com.example.offerdaysongs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "SingerDtoResponse", description = "Singer Info")
public class SingerDtoResponse {

    long id;

    @Schema(name = "name", description = "Singer name", required = true, example = "Madonna")
    String name;
}