package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.request.CopyrightRequest;
import com.example.offerdaysongs.dto.response.SuccessDtoResponse;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.CopyrightSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CopyrightService {

    private final CopyrightRepository copyrightRepository;

    public CopyrightService(CopyrightRepository copyrightRepository) {
        this.copyrightRepository = copyrightRepository;
    }

    public List<Copyright> getAll() {
        return copyrightRepository.findAll();
    }

    public Copyright getById(long id) {
        return copyrightRepository.getById(id);
    }

    public List<Copyright> getByCompany(String companyName) {
        return copyrightRepository.findAll(CopyrightSpecification.copyrightOwnedBy(companyName));
    }

    public List<Copyright> getByRecordingAndDate(String recordingName, ZonedDateTime date) {
        return copyrightRepository.findAll(Specification
                .where(CopyrightSpecification.copyrightOnRecording(recordingName)
                        .and(CopyrightSpecification.copyrightStartBeforeDate(date))
                        .and(CopyrightSpecification.copyrightEndAfterDate(date))));
    }

    public SuccessDtoResponse getTotalTaxByRecordingAndDate(String recordingName, ZonedDateTime date) {
        List<Copyright> copyrights = copyrightRepository.findAll(Specification
                .where(CopyrightSpecification.copyrightOnRecording(recordingName)
                        .and(CopyrightSpecification.copyrightStartBeforeDate(date))
                        .and(CopyrightSpecification.copyrightEndAfterDate(date))));
        return new SuccessDtoResponse(String.format("Total Tax by Recording and Date = %.2f", copyrights
                .stream()
                .mapToDouble(Copyright::getTax)
                .sum()));
    }

    @Transactional
    public Copyright create(CopyrightRequest request) {
        return copyrightRepository.save(convertToCopyright(request));
    }

    @Transactional
    public Copyright update(long id, CopyrightRequest request) {
        Copyright copyright = convertToCopyright(request);
        copyright.setId(id);
        return copyrightRepository.save(copyright);
    }

    @Transactional
    public void deleteById(long id) {
        copyrightRepository.deleteById(id);
    }

    private Copyright convertToCopyright(CopyrightRequest request) {
        Company company = new Company(request.getCompany().getId(), request.getCompany().getName());
        Singer singer =
                new Singer(request.getRecording().getSingerDto().getId(), request.getRecording().getSingerDto().getName());
        Recording recording = new Recording(0L, request.getRecording().getSongCode(), request.getRecording().getTitle(),
                request.getRecording().getVersion(), request.getRecording().getReleaseTime(), singer);
        return new Copyright(0, recording, company,
                request.getStartDate(),
                request.getEndDate(),
                request.getTax());
    }
}