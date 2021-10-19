package com.example.offerdaysongs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="SuccessDtoResponse", description = "SuccessDtoResponse")
public class SuccessDtoResponse {

    @Schema(name = "description", description = "Text message of Success", required = true, example = "Success")
    String description;
}
