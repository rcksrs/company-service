package com.rcksrs.companyservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.exception.BusinessException;
import com.rcksrs.companyservice.exception.ResourceNotFoundException;
import com.rcksrs.companyservice.repository.CompanyRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CompanyService {
	
	private CompanyRepository companyRepository;
	
	public Company findById(String companyId) {
		return companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Company findByCnpj(String cnpj) {
		return companyRepository.findByCnpj(cnpj).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Company findByName(String name) {
		return companyRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException());
	}
	
	public Page<Company> findAll(Pageable pageable) {
		return companyRepository.findAll(pageable);
	}
	
	public Page<Company> findAllByName(String name, Pageable pageable) {
		return companyRepository.findByNameContainingIgnoreCase(name, pageable);
	}
	
	public Page<Company> findAllByStateAndCity(String state, String city, Pageable pageable) {
		return companyRepository.findByAddressStateAndAddressCity(state, city, pageable);
	}
	
	public Company save(Company company) {
		var canSave = company.getId() == null && companyRepository.findByCnpj(company.getCnpj()).isEmpty();
		if(canSave) return companyRepository.save(company);
		throw new BusinessException("Cannot save an existent company");
	}
	
	public Company update(Company company) {
		companyRepository.findByIdAndCnpj(company.getId(), company.getCnpj()).orElseThrow(() -> new BusinessException("Company's CNPJ cannot be changed"));
		return companyRepository.save(company);
	}
	
	public void delete(Company company) {
		var companySaved = companyRepository.findById(company.getId()).orElseThrow(() -> new ResourceNotFoundException());
		companyRepository.deleteById(companySaved.getId());
	}
	

}
