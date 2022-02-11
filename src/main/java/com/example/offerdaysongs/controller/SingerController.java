package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.service.SingerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/singers")
public class SingerController {

    private static final String ID = "id";
    private final SingerService singerService;

    public SingerController(SingerService singerService) {
        this.singerService = singerService;
    }

    @GetMapping("/{id:[\\d]+}")
    public SingerDtoResponse get(@PathVariable(ID) long id) {
        return convertToDto(singerService.getById(id));
    }

    @GetMapping("/")
    public List<SingerDtoResponse> getAll() {
        return singerService.getAllSingers().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public SingerDtoResponse create(@RequestBody SingerRequest request) {
        return convertToDto(singerService.create(request));
    }

    private SingerDtoResponse convertToDto(Singer singer) {
        return new SingerDtoResponse(singer.getId(), singer.getName());
    }
}
