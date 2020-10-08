package com.rcksrs.companyservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rcksrs.companyservice.domain.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
	Optional<Company> findByCnpj(String cnpj);
	Optional<Company> findByName(String name);
	List<Company> findByNameContainingIgnoreCase(String name);
	List<Company> findByAddressCity(String city);
	List<Company> findByAddressStateAndCity(String state, String city);

}
