package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.CopyrightRequest;
import com.example.offerdaysongs.dto.response.*;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/copyrights")
@Tag(name = "copyright", description = "the Copyright API")
public class CopyrightController {

    private static final String ID = "id";

    private final CopyrightService copyrightService;

    public CopyrightController(CopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @Operation(summary = "Get all Copyrights",
            description = "Returns a list of Copyrights",
            operationId = "copyrights")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CopyrightDtoResponse.class))))})
    @GetMapping("/")
    public ResponseEntity<List<CopyrightDtoResponse>> getAll() {
        List<CopyrightDtoResponse> copyrightDtoResponses = copyrightService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(copyrightDtoResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get Copyright by ID",
            description = "Returns Copyright by ID",
            operationId = "copyright")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = CopyrightDtoResponse.class)))})
    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<CopyrightDtoResponse> getById(@PathVariable(ID) long id) {
        return new ResponseEntity<>(convertToDto(copyrightService.getById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Get all Copyrights by Company",
            description = "Returns a list of Copyrights of target Company",
            operationId = "copyrightsByCompany")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CopyrightDtoResponse.class))))})
    @GetMapping("/searchByCompany")
    public ResponseEntity<List<CopyrightDtoResponse>> getByCompany(@RequestParam(name = "company") String companyName) {
        List<CopyrightDtoResponse> copyrightDtoResponses = copyrightService.getByCompany(companyName)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(copyrightDtoResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get all Copyrights by Recording and Date",
            description = "Returns a list of Copyrights of target Recording on target Date",
            operationId = "copyrightsByRecordingAndDate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CopyrightDtoResponse.class))))})
    @GetMapping("/searchByRecordingAndDate")
    public ResponseEntity<List<CopyrightDtoResponse>> getByRecordingAndDate(
            @RequestParam(name = "recording") String recordingTitle,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<CopyrightDtoResponse> copyrightDtoResponses = copyrightService.getByRecordingAndDate(recordingTitle,
                        ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(copyrightDtoResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get Total Tax by Recording and Date",
            description = "Returns calculated Total Tax by Recording and Date",
            operationId = "successTotalTax")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = SuccessDtoResponse.class)))})
    @GetMapping("/calculateTotalTaxByRecordingAndDate")
    public ResponseEntity<SuccessDtoResponse> getTotalTaxByRecordingAndDate(
            @RequestParam(name = "recording") String recordingTitle,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return new ResponseEntity<>(copyrightService.getTotalTaxByRecordingAndDate(recordingTitle,
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault())), HttpStatus.OK);
    }

    @Operation(summary = "Create Copyright",
            description = "Returns new Copyright, if created",
            operationId = "copyrightCreate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = CopyrightDtoResponse.class)))})
    @PostMapping("/")
    public ResponseEntity<CopyrightDtoResponse> create(@RequestBody CopyrightRequest request) {
        return new ResponseEntity<>(convertToDto(copyrightService.create(request)), HttpStatus.OK);
    }

    @Operation(summary = "Update Copyright",
            description = "Returns new Copyright, if updated",
            operationId = "copyrightUpdate")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = CopyrightDtoResponse.class)))})
    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<CopyrightDtoResponse> update(@PathVariable(ID) long id, @RequestBody CopyrightRequest request) {
        return new ResponseEntity<>(convertToDto(copyrightService.update(id, request)), HttpStatus.OK);
    }

    @Operation(summary = "Delete Copyright",
            description = "Returns new Success, if deleted",
            operationId = "copyrightDelete")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = SuccessDtoResponse.class)))})
    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<SuccessDtoResponse> delete(@PathVariable(ID) long id) {
        copyrightService.deleteById(id);
        return new ResponseEntity<>(new SuccessDtoResponse("Success"), HttpStatus.OK);
    }

    private CopyrightDtoResponse convertToDto(Copyright copyright) {
        CompanyDtoResponse companyDtoResponse =
                new CompanyDtoResponse(copyright.getCompany().getId(), copyright.getCompany().getName());
        SingerDtoResponse singerDtoResponse = new SingerDtoResponse(copyright.getRecording().getSinger().getId(),
                copyright.getRecording().getSinger().getName());
        RecordingDtoResponse recordingDtoResponse =
                new RecordingDtoResponse(copyright.getRecording().getId(), copyright.getRecording().getSongCode(),
                        copyright.getRecording().getTitle(), copyright.getRecording().getVersion(),
                        copyright.getRecording().getReleaseTime(), singerDtoResponse);
        return new CopyrightDtoResponse(
                copyright.getId(),
                recordingDtoResponse,
                companyDtoResponse,
                copyright.getStartDate(),
                copyright.getEndDate(), copyright.getTax());
    }
}