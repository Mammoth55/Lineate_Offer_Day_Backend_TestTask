package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.response.CompanyDtoResponse;
import com.example.offerdaysongs.dto.request.CompanyRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.service.CompanyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private static final String ID = "id";
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/")
    public List<CompanyDtoResponse> getAll() {
        return companyService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public CompanyDtoResponse get(@PathVariable(ID) long id) {
        return convertToDto(companyService.getById(id));
    }

    @PostMapping("/")
    public CompanyDtoResponse create(@RequestBody CompanyRequest request) {
        return convertToDto(companyService.create(request));
    }

    private CompanyDtoResponse convertToDto(Company company) {
        return new CompanyDtoResponse(company.getId(), company.getName());
     }
}
