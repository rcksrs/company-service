package com.rcksrs.companyservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {
	
	private CompanyRepository companyRepository;
	
	public Company findById(String companyId) {
		return companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Error"));
	}
	
	public Company findByCnpj(String cnpj) {
		return companyRepository.findByCnpj(cnpj).orElseThrow(() -> new RuntimeException("Error"));
	}
	
	public Company findByNameCnpj(String name) {
		return companyRepository.findByName(name).orElseThrow(() -> new RuntimeException("Error"));
	}
	
	public List<Company> findAllByName(String name) {
		return companyRepository.findByNameContainingIgnoreCase(name);
	}

}
