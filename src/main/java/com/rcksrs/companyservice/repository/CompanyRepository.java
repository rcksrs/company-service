package com.rcksrs.companyservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.rcksrs.companyservice.domain.Company;

public interface CompanyRepository extends MongoRepository<Company, String> {
	Optional<Company> findByCnpj(String cnpj);
	Optional<Company> findByIdAndCnpj(String id, String cnpj);
	Optional<Company> findByName(String name);
	Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);
	Page<Company> findByAddressCountryAndAddressState(String country, String state, Pageable pageable);
	Page<Company> findByAddressStateAndAddressCity(String state, String city, Pageable pageable);

}
