package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.CopyrightRequest;
import com.example.offerdaysongs.dto.response.*;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.service.CopyrightService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/copyrights")
public class CopyrightController {

    private static final String ID = "id";

    private final CopyrightService copyrightService;

    public CopyrightController(CopyrightService copyrightService) {
        this.copyrightService = copyrightService;
    }

    @GetMapping("/")
    public List<CopyrightDtoResponse> getAll() {
        return copyrightService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public CopyrightDtoResponse getById(@PathVariable(ID) long id) {
        return convertToDto(copyrightService.getById(id));
    }

    @GetMapping("/searchByCompany")
    public List<CopyrightDtoResponse> getByCompany(@RequestParam(name = "company") String companyName) {
        return copyrightService.getByCompany(companyName).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/searchByRecordingAndDate")
    public List<CopyrightDtoResponse> getByRecordingAndDate(
            @RequestParam(name = "recording") String recordingTitle,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return copyrightService.getByRecordingAndDate(recordingTitle,
                        ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()))
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/calculateTotalTaxByRecordingAndDate")
    public SuccessDtoResponse getTotalTaxByRecordingAndDate(
            @RequestParam(name = "recording") String recordingTitle,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return copyrightService.getTotalTaxByRecordingAndDate(recordingTitle,
                ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()));
    }

    @PostMapping("/")
    public CopyrightDtoResponse create(@RequestBody CopyrightRequest request) {
        return convertToDto(copyrightService.create(request));
    }

    @PutMapping("/{id:[\\d]+}")
    public CopyrightDtoResponse update(@PathVariable(ID) long id, @RequestBody CopyrightRequest request) {
        return convertToDto(copyrightService.update(id, request));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public SuccessDtoResponse delete(@PathVariable(ID) long id) {
        copyrightService.deleteById(id);
        return new SuccessDtoResponse("Success");
    }

    private CopyrightDtoResponse convertToDto(Copyright copyright) {
        CompanyDtoResponse companyDtoResponse =
                new CompanyDtoResponse(copyright.getCompany().getId(), copyright.getCompany().getName());
        SingerDtoResponse singerDtoResponse = new SingerDtoResponse(copyright.getRecording().getSinger().getId(),
                copyright.getRecording().getSinger().getName());
        RecordingDtoResponse recordingDtoResponse =
                new RecordingDtoResponse(copyright.getRecording().getId(), copyright.getRecording().getTitle(),
                        copyright.getRecording().getVersion(), copyright.getRecording().getReleaseTime(), singerDtoResponse);
        return new CopyrightDtoResponse(
                copyright.getId(),
                recordingDtoResponse,
                companyDtoResponse,
                copyright.getStartDate(),
                copyright.getEndDate(), copyright.getTax());
    }
}