package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.request.CompanyRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.service.CompanyService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    final static String TEST_COMPANY_NAME1 = "DQ Digital";
    final static String TEST_COMPANY_NAME2 = "New Wave Studio";

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CompanyService companyService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CompanyController(companyService)).build();
    }

    @Test
    public void getAllTest() throws Exception {
        Company company1 = new Company(5, TEST_COMPANY_NAME1);
        Company company2 = new Company(6, TEST_COMPANY_NAME2);
        List<Company> companies = List.of(company1, company2);

        when(companyService.getAll()).thenReturn(companies);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/companies/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(TEST_COMPANY_NAME1)))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo(TEST_COMPANY_NAME2)));
    }

    @Test
    public void getByIdTest() throws Exception {
        Company company = new Company(6, TEST_COMPANY_NAME2);

        when(companyService.getById(6)).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/companies/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(6)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_COMPANY_NAME2)));
    }

    @Test
    public void createTest() throws Exception {
        Company company = new Company(5, TEST_COMPANY_NAME1);

        when(companyService.create(any())).thenReturn(company);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/companies/")
                        .content(objectMapper.writeValueAsString(new CompanyRequest(company.getName())))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(5)))
                .andExpect(jsonPath("$.name", Matchers.equalTo(TEST_COMPANY_NAME1)));
    }
}