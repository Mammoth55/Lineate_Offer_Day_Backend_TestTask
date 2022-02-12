package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.service.SingerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public ResponseEntity<List<SingerDtoResponse>> getAll() {
        return ResponseEntity.ok().body(singerService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<SingerDtoResponse> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertToDto(singerService.getById(id)));
    }

    @GetMapping("/filter")
    public ResponseEntity<SingerDtoResponse> getByName(@RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok().body(convertToDto(singerService.getByName(name)));
    }

    @PostMapping("/")
    public ResponseEntity<SingerDtoResponse> create(@RequestBody SingerRequest request) {
        return ResponseEntity.ok().body(convertToDto(singerService.create(request)));
    }

    private SingerDtoResponse convertToDto(Singer singer) {
        return new SingerDtoResponse(singer.getId(), singer.getName());
    }
}