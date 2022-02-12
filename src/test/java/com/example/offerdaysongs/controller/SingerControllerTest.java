package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.SingerRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.service.SingerService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SingerControllerTest {

    final static String TEST_SINGER_NAME1 = "Dimash Qudaibergen";
    final static String TEST_SINGER_NAME2 = "Andrea Bochelli";

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private SingerService singerService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SingerController(singerService)).build();
    }

    @Test
    public void getByIdTest() throws Exception {
        Singer singer = new Singer(6, TEST_SINGER_NAME1);

        when(singerService.getById(6)).thenReturn(singer);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/singers/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_SINGER_NAME1)));
    }

    @Test
    public void getAllTest() throws Exception {
        Singer singer1 = new Singer(5, TEST_SINGER_NAME1);
        Singer singer2 = new Singer(6, TEST_SINGER_NAME2);
        List<Singer> singers = List.of(singer1, singer2);

        when(singerService.getAll()).thenReturn(singers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/singers/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(TEST_SINGER_NAME1)))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo(TEST_SINGER_NAME2)));
    }

    @Test
    public void createTest() throws Exception {
        Singer singer = new Singer(5, TEST_SINGER_NAME1);

        when(singerService.create(any())).thenReturn(singer);

        mockMvc.perform(post("/api/singers/")
                        .content(objectMapper.writeValueAsString(new SingerRequest(singer.getName())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_SINGER_NAME1)));
    }
}