package com.rcksrs.companyservice.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.domain.dto.CompanyDTO;
import com.rcksrs.companyservice.service.CompanyService;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CompanyController {
	
	private CompanyService companyService;
	
	@GetMapping
	@ApiOperation("Return all companies that are registered")
	public ResponseEntity<Page<Company>> findAll(@PageableDefault(sort = "name", size = 20) Pageable pageable) {
		var companies = companyService.findAll(pageable);
		return ResponseEntity.ok(companies);
	}
	
	@GetMapping("/filter/name/{name}")
	@ApiOperation("Return all companies that are registered filtered by name")
	public ResponseEntity<Page<Company>> findAllByName(@PathVariable String name, @PageableDefault(sort = "name", size = 20) Pageable pageable) {
		var companies = companyService.findAllByName(name, pageable);
		return ResponseEntity.ok(companies);
	}
	
	@GetMapping("/filter/locale/{state}/{city}")
	@ApiOperation("Return all companies that are registered filtered by state and city")
	public ResponseEntity<Page<Company>> findAllByStateAndCity(@PathVariable String state, @PathVariable String city, @PageableDefault(sort = "name", size = 20) Pageable pageable) {
		var companies = companyService.findAllByStateAndCity(state, city, pageable);
		return ResponseEntity.ok(companies);
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Find a company by id")
	public ResponseEntity<Company> findById(@PathVariable String id) {
		var company = companyService.findById(id);
		return ResponseEntity.ok(company);
	}
	
	@GetMapping("/find/{id}")
	@ApiOperation("Find a dto of company by id")
	public ResponseEntity<CompanyDTO> findCompanyById(@PathVariable String id) {
		var company = companyService.findById(id);
		return ResponseEntity.ok(CompanyDTO.fromCompany(company));
	}
	
	@GetMapping("/name/{name}")
	@ApiOperation("Find a company by name")
	public ResponseEntity<Company> findByName(@PathVariable String name) {
		var company = companyService.findByName(name);
		return ResponseEntity.ok(company);
	}
	
	@GetMapping("/cnpj/{cnpj}")
	@ApiOperation("Find a company by CNPJ")
	public ResponseEntity<Company> findByCnpj(@PathVariable String cnpj) {
		var company = companyService.findByCnpj(cnpj);
		return ResponseEntity.ok(company);
	}
	
	@PostMapping
	@ApiOperation("Save a company")
	public ResponseEntity<Company> save(@RequestBody @Valid Company company) {
		var companySaved = companyService.save(company);
		var location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(companySaved.getId()).toUri();
		return ResponseEntity.created(location).body(companySaved);
	}
	
	@PutMapping
	@ApiOperation("Update a company")
	public ResponseEntity<Company> update(@RequestBody @Valid Company company) {
		var companySaved = companyService.update(company);
		return ResponseEntity.ok(companySaved);
	}
	
	@DeleteMapping
	@ApiOperation("Delete a company")
	public ResponseEntity<Company> delete(@RequestBody @Valid Company company) {
		companyService.delete(company);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

}
