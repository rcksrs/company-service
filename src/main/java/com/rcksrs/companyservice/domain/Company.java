package com.rcksrs.companyservice.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Document
@Data @Builder
public class Company {
	
	@Id
	private String id;
	
	@Indexed
	private String name;
	
	@Indexed
	private String cnpj;
	
	private LocalDate openingDate;
	
	private Address address;
	
	private List<Contact> contacts;

}
