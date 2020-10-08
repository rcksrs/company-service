package com.rcksrs.companyservice.domain;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class Address {
	private String zipCode;
	private String country;
	private String state;
	private String city;
	private String street;

}
