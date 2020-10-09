package com.rcksrs.companyservice.domain.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.domain.Contact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO implements Serializable {
	private static final long serialVersionUID = 2694688469613313534L;
	
	@NotBlank(message = "Fill in the id field")
	private String id;
	private String name;
	private String cnpj;
	private String country;
	private String state;
	private String city;
	private List<Contact> contacts;
	
	public static CompanyDTO fromCompany(Company company) {
		return CompanyDTO.builder()
				.id(company.getId())
				.name(company.getName())
				.cnpj(company.getCnpj())
				.country(company.getAddress().getCountry())
				.state(company.getAddress().getState())
				.city(company.getAddress().getCity())
				.contacts(company.getContacts())
				.build();
	}

}
