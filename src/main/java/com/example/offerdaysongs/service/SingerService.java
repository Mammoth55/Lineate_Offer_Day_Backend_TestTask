package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.SingerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerService {

    private final SingerRepository singerRepository;

    public SingerService(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    public List<Singer> getAll() {
        return singerRepository.findAll();
    }

    public Singer getById(long id)  {
        return singerRepository.getById(id);
    }

    public Singer getByName(String name) {
        return singerRepository.getByName(name);
    }

    public Singer create(SingerRequest request) {
        return singerRepository.save(Singer.builder().name(request.getName()).build());
    }
}