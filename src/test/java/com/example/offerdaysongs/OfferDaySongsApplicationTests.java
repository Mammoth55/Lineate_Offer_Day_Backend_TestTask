package com.example.offerdaysongs;

import com.example.offerdaysongs.dto.request.CompanyRequest;
import com.example.offerdaysongs.dto.request.CopyrightRequest;
import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.dto.response.CompanyDtoResponse;
import com.example.offerdaysongs.dto.response.RecordingDtoResponse;
import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.repository.CompanyRepository;
import com.example.offerdaysongs.repository.CopyrightRepository;
import com.example.offerdaysongs.repository.RecordingRepository;
import com.example.offerdaysongs.repository.SingerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfferDaySongsApplicationTests {

    final static String TEST_COMPANY_NAME = "DQ Digital";
    final static String TEST_SINGER_NAME = "Dimash Qudaibergen";
    final static String TEST_RECORDING_TITLE = "Stranger";
    final static double TEST_COPYRIGHT_TOTAL_TAX = 99.95;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private SingerRepository singerRepository;

    @MockBean
    private RecordingRepository recordingRepository;

    @MockBean
    private CopyrightRepository copyrightRepository;

    @Test
    public void whenCreateCompany() throws Exception {
        Company company = new Company(5, TEST_COMPANY_NAME);

        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(company);

        mockMvc.perform(post("/api/companies/")
                        .content(objectMapper.writeValueAsString(new CompanyRequest(company.getName())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(TEST_COMPANY_NAME));
    }

    @Test
    public void whenCreateSinger() throws Exception {
        Singer singer = new Singer(4, TEST_SINGER_NAME);

        Mockito.when(singerRepository.save(Mockito.any())).thenReturn(singer);

        mockMvc.perform(post("/api/singers/")
                        .content(objectMapper.writeValueAsString(new SingerRequest(singer.getName())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(TEST_SINGER_NAME));
    }

    @Test
    public void whenCreateRecording() throws Exception {
        Singer singer = new Singer(4, TEST_SINGER_NAME);
        Recording recording = new Recording(4L, "HITS", TEST_RECORDING_TITLE, "ORIGINAL",
                ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")), singer);

        Mockito.when(recordingRepository.save(Mockito.any())).thenReturn(recording);

        mockMvc.perform(post("/api/recordings/")
                        .content(objectMapper.writeValueAsString(recording))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(TEST_RECORDING_TITLE));
    }

    @Test
    public void whenCreateCopyright() throws Exception {
        Company company = new Company(4, TEST_COMPANY_NAME);
        Singer singer = new Singer(4, TEST_SINGER_NAME);
        Recording recording = new Recording(4L, "HITS", TEST_RECORDING_TITLE, "ORIGINAL",
                ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")), singer);
        Copyright copyright = new Copyright(0, recording, company,
                ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")),
                ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")),
                TEST_COPYRIGHT_TOTAL_TAX);

        Mockito.when(copyrightRepository.save(Mockito.any())).thenReturn(copyright);

        mockMvc.perform(post("/api/copyrights/")
                        .content(objectMapper.writeValueAsString(convertFromCopyright(copyright)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.company.name").value(TEST_COMPANY_NAME))
                .andExpect(jsonPath("$.tax").value(TEST_COPYRIGHT_TOTAL_TAX));
    }

    private SingerDtoResponse convertToSinger(Singer singer) {
        return new SingerDtoResponse(singer.getId(), singer.getName());
    }

    private CompanyDtoResponse convertFromCompany(Company company) {
        return new CompanyDtoResponse(company.getId(), company.getName());
    }

    private RecordingDtoResponse convertFromRecording(Recording recording) {
        return new RecordingDtoResponse(0,
                recording.getSongCode(),
                recording.getTitle(),
                recording.getVersion(),
                recording.getReleaseTime(),
                convertToSinger(recording.getSinger()));
    }

    private CopyrightRequest convertFromCopyright(Copyright copyright) {
        return new CopyrightRequest(
                convertFromRecording(copyright.getRecording()),
                convertFromCompany(copyright.getCompany()),
                copyright.getStartDate(),
                copyright.getEndDate(),
                copyright.getTax());
    }
}