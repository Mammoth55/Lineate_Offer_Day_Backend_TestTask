package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.response.RecordingDto;
import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.dto.request.RecordingRequest;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.service.RecordingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recordings")
public class RecordingController {

    private static final String ID = "id";
    private final RecordingService recordingService;

    public RecordingController(RecordingService recordingService) {
        this.recordingService = recordingService;
    }

    @GetMapping("/")
    public ResponseEntity<List<RecordingDto>> getAll() {
        return ResponseEntity.ok().body(recordingService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<RecordingDto> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertToDto(recordingService.getById(id)));
    }

    @PostMapping("/")
    public ResponseEntity<RecordingDto> create(@RequestBody RecordingRequest request) {
        return ResponseEntity.ok().body(convertToDto(recordingService.create(request)));
    }

    private RecordingDto convertToDto(Recording recording) {
        var singer = recording.getSinger();
        return new RecordingDto(recording.getId(),
                                recording.getSongCode(),
                                recording.getTitle(),
                                recording.getVersion(),
                                recording.getReleaseTime().toString(),
                                singer != null ? new SingerDtoResponse(singer.getId(), singer.getName()) : null);
    }
}