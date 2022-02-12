package com.example.offerdaysongs.controller;

import com.example.offerdaysongs.dto.response.CompanyDto;
import com.example.offerdaysongs.dto.request.CompanyRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.service.CompanyService;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CompanyDto>> getAll() {
        return ResponseEntity.ok().body(companyService.getAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<CompanyDto> getById(@PathVariable(ID) long id) {
        return ResponseEntity.ok().body(convertToDto(companyService.getById(id)));
    }

    @PostMapping("/")
    public ResponseEntity<CompanyDto> create(@RequestBody CompanyRequest request) {
        return ResponseEntity.ok().body(convertToDto(companyService.create(request)));
    }

    private CompanyDto convertToDto(Company company) {
        return new CompanyDto(company.getId(), company.getName());
     }
}
