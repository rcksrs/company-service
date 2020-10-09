package com.rcksrs.companyservice.domain;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
	
	@Id
	private String id;
	
	@Indexed
	@NotBlank(message = "Fill in the name field")
	private String name;
	
	@Indexed
	@NotBlank(message = "Fill in the cnpj field")
	@CNPJ(message = "The CNPJ provided is invalid")
	private String cnpj;
	
	private LocalDate openingDate;
	
	@NotNull(message = "Fill in the address field")
	private Address address;
	
	@NotNull(message = "Fill in at least one contact field")
	private List<Contact> contacts;

}
