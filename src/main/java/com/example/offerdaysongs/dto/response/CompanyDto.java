package com.example.offerdaysongs.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CompanyDtoResponse", description = "Company Info")
public class CompanyDto {

    long id;

    @Schema(name = "name", description = "Company name", required = true, example = "Microsoft")
    String name;
}