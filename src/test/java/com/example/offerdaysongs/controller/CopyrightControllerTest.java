package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.CopyrightRequest;
import com.example.offerdaysongs.dto.response.CompanyDto;
import com.example.offerdaysongs.dto.response.RecordingDto;
import com.example.offerdaysongs.dto.response.SingerDtoResponse;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.model.Copyright;
import com.example.offerdaysongs.model.Recording;
import com.example.offerdaysongs.model.Singer;
import com.example.offerdaysongs.service.CopyrightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CopyrightControllerTest {

    final static String TEST_SINGER_NAME1 = "Dimash Qudaibergen";
    final static String TEST_RECORDING_TITLE1 = "Stranger";
    final static String TEST_COMPANY_NAME1 = "DQ Digital";
    final static String TEST_COMPANY_NAME2 = "NewWaveStudio";
    final static double TEST_COPYRIGHT_TOTAL_TAX = 99.95;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CopyrightService copyrightService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CopyrightController(copyrightService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        Company company1 = new Company(5, TEST_COMPANY_NAME1);
        Company company2 = new Company(6, TEST_COMPANY_NAME2);
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("1")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();
        Copyright copyright1 = Copyright.builder()
                .id(3)
                .recording(recording)
                .company(company1)
                .startDate(ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")))
                .endDate(ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")))
                .tax(TEST_COPYRIGHT_TOTAL_TAX)
                .build();
        Copyright copyright2 = Copyright.builder()
                .id(4)
                .recording(recording)
                .company(company2)
                .startDate(ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")))
                .endDate(ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")))
                .tax(TEST_COPYRIGHT_TOTAL_TAX)
                .build();
        List<Copyright> copyrights = List.of(copyright1, copyright2);

        when(copyrightService.getAll()).thenReturn(copyrights);

        mockMvc.perform(get("/api/copyrights/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].company.name", Matchers.equalTo(TEST_COMPANY_NAME1)))
                .andExpect(jsonPath("$[1].company.name", Matchers.equalTo(TEST_COMPANY_NAME2)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Company company = new Company(5, TEST_COMPANY_NAME1);
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("1")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();
        Copyright copyright = Copyright.builder()
                .id(3)
                .recording(recording)
                .company(company)
                .startDate(ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")))
                .endDate(ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")))
                .tax(TEST_COPYRIGHT_TOTAL_TAX)
                .build();

        when(copyrightService.getById(3)).thenReturn(copyright);

        mockMvc.perform(get("/api/copyrights/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.company.name", Matchers.equalTo(TEST_COMPANY_NAME1)))
                .andExpect(jsonPath("$.tax", Matchers.equalTo(TEST_COPYRIGHT_TOTAL_TAX)));
    }

    @Test
    public void getByCompanyTest() throws Exception {
        Company company2 = new Company(6, TEST_COMPANY_NAME2);
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("1")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();
        Copyright copyright2 = Copyright.builder()
                .id(4)
                .recording(recording)
                .company(company2)
                .startDate(ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")))
                .endDate(ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")))
                .tax(TEST_COPYRIGHT_TOTAL_TAX)
                .build();
        List<Copyright> copyrights = List.of(copyright2);

        when(copyrightService.getByCompany(any())).thenReturn(copyrights);

        mockMvc.perform(get("/api/copyrights/searchByCompany?company=NewWaveStudio"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].tax", Matchers.equalTo(TEST_COPYRIGHT_TOTAL_TAX)))
                .andExpect(jsonPath("$[0].company.name", Matchers.equalTo(TEST_COMPANY_NAME2)));
    }

    @Test
    public void getByRecordingAndDateTest() {
    }

    @Test
    public void getTotalTaxByRecordingAndDateTest() {
    }

    @Test
    public void createTest() throws Exception {
        Company company = new Company(5, TEST_COMPANY_NAME1);
        Singer singer = new Singer(5, TEST_SINGER_NAME1);
        Recording recording = Recording.builder()
                .id(6L)
                .songCode("SN10")
                .title(TEST_RECORDING_TITLE1)
                .version("1")
                .releaseTime(ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow")))
                .singer(singer)
                .build();
        Copyright copyright = Copyright.builder()
                .id(3)
                .recording(recording)
                .company(company)
                .startDate(ZonedDateTime.of(2017, 6, 6, 15, 0, 0, 0,
                        ZoneId.of("Europe/Moscow")))
                .endDate(ZonedDateTime.of(2050, 12, 31, 23, 59, 59, 0,
                        ZoneId.of("Europe/Moscow")))
                .tax(TEST_COPYRIGHT_TOTAL_TAX)
                .build();

        when(copyrightService.create(Mockito.any())).thenReturn(copyright);

        mockMvc.perform(post("/api/copyrights/")
                        .content(objectMapper.writeValueAsString(convertToRequestFromCopyright(copyright)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(3)))
                .andExpect(jsonPath("$.tax", Matchers.equalTo(TEST_COPYRIGHT_TOTAL_TAX)));
    }

    @Test
    public void updateTest() {
    }

    @Test
    public void deleteTest() {
    }

    private CopyrightRequest convertToRequestFromCopyright(Copyright copyright) {
        return new CopyrightRequest(
                convertFromRecording(copyright.getRecording()),
                convertFromCompany(copyright.getCompany()),
                copyright.getStartDate().toString(),
                copyright.getEndDate().toString(),
                copyright.getTax());
    }

    private CompanyDto convertFromCompany(Company company) {
        return new CompanyDto(company.getId(), company.getName());
    }

    private RecordingDto convertFromRecording(Recording recording) {
        return new RecordingDto(recording.getId(),
                recording.getSongCode(),
                recording.getTitle(),
                recording.getVersion(),
                recording.getReleaseTime().toString(),
                convertFromSinger(recording.getSinger()));
    }

    private SingerDtoResponse convertFromSinger(Singer singer) {
        return new SingerDtoResponse(singer.getId(), singer.getName());
    }
}