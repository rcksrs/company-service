package com.rcksrs.companyservice.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Contact {
	@NotBlank(message = "Fill in the contact field")
	private String contact;
	
	@NotNull(message = "Fill in the contact type field")
	private ContactType type;

}
