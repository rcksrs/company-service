package com.rcksrs.companyservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	
	public Page<Company> findAllByName(String name, Pageable pageable) {
		return companyRepository.findByNameContainingIgnoreCase(name, pageable);
	}
	
	public Page<Company> findAllByCity(String city, Pageable pageable) {
		return companyRepository.findByAddressCity(city, pageable);
	}
	
	public Page<Company> findAllByStateAndCity(String state, String city, Pageable pageable) {
		return companyRepository.findByAddressStateAndCity(state, city, pageable);
	}
	
	public Company save(Company company) {
		var canSave = company.getId() == null && companyRepository.findByCnpj(company.getCnpj()).isEmpty();
		if(canSave) return companyRepository.save(company);
		throw new RuntimeException("Error");
	}
	
	public Company update(Company company) {
		companyRepository.findByIdAndCnpj(company.getId(), company.getCnpj()).orElseThrow(() -> new RuntimeException("Error"));
		return companyRepository.save(company);
	}
	
	public void delete(Company company) {
		var companySaved = companyRepository.findById(company.getId()).orElseThrow(() -> new RuntimeException("Error"));
		companyRepository.deleteById(companySaved.getId());		
	}
	

}
