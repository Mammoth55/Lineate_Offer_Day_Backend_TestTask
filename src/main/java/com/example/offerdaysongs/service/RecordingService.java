package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.request.RecordingRequest;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        Recording recording = new Recording();
        recording.setSongCode(request.getSongCode());
        recording.setTitle(request.getTitle());
        recording.setVersion(request.getVersion());
        recording.setReleaseTime(request.getReleaseTime());
        var singerDto = request.getSinger();
        if (singerDto != null) {
            var singer = singerRepository.findById(singerDto.getId()).orElseGet(() -> {
                var temp = new Singer();
                temp.setName(singerDto.getName());
                return singerRepository.save(temp);
            });
            recording.setSinger(singer);
        }
        return recordingRepository.save(recording);
    }
}