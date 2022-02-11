package com.example.offerdaysongs.service;

import com.example.offerdaysongs.dto.request.CompanyRequest;
import com.example.offerdaysongs.model.Company;
import com.example.offerdaysongs.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(long id) {
        return companyRepository.getById(id);
    }

    public Company create(CompanyRequest request) {
        Company company = new Company();
        company.setName(request.getName());
        return companyRepository.save(company);
    }
}
