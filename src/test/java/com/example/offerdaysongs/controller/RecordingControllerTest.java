package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.RecordingRequest;
import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.service.RecordingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RecordingControllerTest {

    final static String TEST_SINGER_NAME1 = "Dimash Qudaibergen";
    final static String TEST_SINGER_NAME2 = "Andrea Bochelli";
    final static String TEST_RECORDING_TITLE1 = "Stranger";
    final static String TEST_RECORDING_TITLE2 = "Aida";

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private RecordingService recordingService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RecordingController(recordingService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        Singer singer1 = new Singer(5, TEST_SINGER_NAME1);
        Recording recording1 = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("2")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer1)
                .build();
        Singer singer2 = new Singer(6, TEST_SINGER_NAME2);
        Recording recording2 = Recording.builder()
                .id(7L)
                .songCode("SN20")
                .title(TEST_RECORDING_TITLE2)
                .version("1")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer2)
                .build();
        List<Recording> recordings = List.of(recording1, recording2);

        when(recordingService.getAll()).thenReturn(recordings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recordings/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].title", Matchers.equalTo(TEST_RECORDING_TITLE1)))
                .andExpect(jsonPath("$[1].title", Matchers.equalTo(TEST_RECORDING_TITLE2)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("2")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();

        when(recordingService.getById(6)).thenReturn(recording);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/recordings/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.title", Matchers.equalTo(TEST_RECORDING_TITLE1)));
    }

    @Test
    public void createTest() throws Exception {
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("2")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();

        when(recordingService.create(any())).thenReturn(recording);

        mockMvc.perform(post("/api/recordings/")
                        .content(objectMapper.writeValueAsString(
                                new RecordingRequest("SN10", TEST_RECORDING_TITLE1, "2",
                                        ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")).toString(),
                                        new SingerRequest(singer.getName()))))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.title", Matchers.equalTo(TEST_RECORDING_TITLE1)));
    }
}