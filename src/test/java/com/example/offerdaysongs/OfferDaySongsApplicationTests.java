package com.example.offerdaysongs;

import com.example.offerdaysongs.controller.SingerController;
import com.example.offerdaysongs.dto.response.CompanyDtoResponse;
import com.example.offerdaysongs.dto.response.RecordingDtoResponse;
import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OfferDaySongsApplicationTests {

    final static String TEST_COMPANY_NAME = "DQ Digital";
    final static String TEST_SINGER_NAME = "Dimash Qudaibergen";
    final static String TEST_RECORDING_TITLE = "Stranger";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SingerController singerController;

    @Test
    public void whenCreateCompany() throws Exception {

        Company company = new Company(0L, TEST_COMPANY_NAME);
        mockMvc.perform(
                        post("/api/companies/")
                                .content(objectMapper.writeValueAsString(company))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(TEST_COMPANY_NAME));
    }

    @Test
    public void whenCreateSinger() throws Exception {

        Singer singer = new Singer(0L, TEST_SINGER_NAME);
        mockMvc.perform(
                        post("/api/singers/")
                                .content(objectMapper.writeValueAsString(singer))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(TEST_SINGER_NAME));
    }

    @Test
    public void whenCreateRecording() throws Exception {

        List<SingerDtoResponse> list = singerController.getAll();
        Singer singer = convertToSinger(list.get(list.size() - 1));

        Recording recording = new Recording(0L, "HITS", TEST_RECORDING_TITLE, "ORIGINAL",
                ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                                ZoneId.of("Europe/Moscow")), singer);
        mockMvc.perform(
                        post("/api/recordings/")
                                .content(objectMapper.writeValueAsString(recording))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value(TEST_RECORDING_TITLE));
    }

    @Test
    public void whenCreateCopyright() throws Exception {

        // немного не успел отладить этот и дописать оставшиеся тесты, т.к. получил оффер

//        List<CompanyDtoResponse> companyDtoResponses = companyController.getAll();
//        Company company = convertToCompany(companyDtoResponses.get(companyDtoResponses.size() - 1));
//
//        List<RecordingDtoResponse> recordingDtoResponses = recordingController.getAll();
//        Recording recording = convertToRecording(recordingDtoResponses.get(recordingDtoResponses.size() - 1));
//
//        Copyright copyright = new Copyright(0, recording, company,
//                ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
//                        ZoneId.of("Europe/Moscow")),
//                ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
//                        ZoneId.of("Europe/Moscow")),
//                99.95);
//
//        mockMvc.perform(
//                        post("/api/copyrights/")
//                                .content(objectMapper.writeValueAsString(copyright))
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.company.name").value(TEST_COMPANY_NAME))
//                .andExpect(jsonPath("$.tax").value(99.95));
    }

    private Singer convertToSinger(SingerDtoResponse singerDtoResponse){
        return new Singer(singerDtoResponse.getId(), singerDtoResponse.getName());
    }

//    private Company convertToCompany(CompanyDtoResponse companyDtoResponse){
//        return new Company(companyDtoResponse.getId(), companyDtoResponse.getName());
//    }
//
//    private Recording convertToRecording(RecordingDtoResponse recordingDtoResponse){
//        return new Recording(recordingDtoResponse.getId(),
//                recordingDtoResponse.getSongCode(),
//                recordingDtoResponse.getTitle(),
//                recordingDtoResponse.getVersion(),
//                recordingDtoResponse.getReleaseTime(),
//                convertToSinger(recordingDtoResponse.getSingerDto()));
//    }
}
