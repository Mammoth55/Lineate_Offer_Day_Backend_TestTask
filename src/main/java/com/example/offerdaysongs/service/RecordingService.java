package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.request.RecordingRequest;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RecordingService {

    private final RecordingRepository recordingRepository;
    private final SingerRepository singerRepository;

    public RecordingService(RecordingRepository recordingRepository,
                            SingerRepository singerRepository) {
        this.recordingRepository = recordingRepository;
        this.singerRepository = singerRepository;
    }

    public List<Recording> getAll() {
        return recordingRepository.findAll();
    }

    public Recording getById(long id) {
        return recordingRepository.getById(id);
    }

    @Transactional
    public Recording create(RecordingRequest request) {
        String singerName = request.getSinger().getName();
        Singer singer = singerRepository.getByName(singerName);
        if (singer == null) {
            singer = singerRepository.save(Singer.builder().name(singerName).build());
        }
        return recordingRepository.save(Recording.builder()
                .songCode(request.getSongCode())
                .title(request.getTitle())
                .version(request.getVersion())
                .releaseTime(ZonedDateTime.parse(request.getReleaseTime()))
                .singer(singer)
                .build());
    }
}