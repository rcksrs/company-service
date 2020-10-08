package com.rcksrs.companyservice.domain.dto;

import java.util.List;

import com.rcksrs.companyservice.domain.Contact;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CompanyDTO {
	private String id;
	private String name;
	private String cnpj;
	private String country;
	private String state;
	private String city;
	private List<Contact> contacts;

}
