package com.rcksrs.companyservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rcksrs.companyservice.domain.Address;
import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.domain.Contact;
import com.rcksrs.companyservice.domain.ContactType;
import com.rcksrs.companyservice.exception.ResourceNotFoundException;
import com.rcksrs.companyservice.service.CompanyService;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CompanyService companyService;
	
	private static ObjectMapper mapper;
	
	private static Company company;
	private static Page<Company> companies;
	
	@BeforeAll
	static void beforeAll() {
		mapper = new ObjectMapper();
		company = Company.builder()
				.id("1")
				.cnpj("23647496000195")
				.name("Lorem ipsum dolor")
				.address(new Address("02319160", "Brazil", "Maranhao", "Sao Luis", "Rua Cargo 977"))
				.contacts(List.of(new Contact("9827849063", ContactType.PHONE)))
				.build();
		
		companies = new PageImpl<>(List.of(company));
	}

	@Test
	@DisplayName("Should return status code 200 when find a company by id")
	void testFindById() throws Exception {
		when(companyService.findById("1")).thenReturn(company);
		
		mockMvc.perform(get("/api/v1/1")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(company)));
	}

	@Test
	@DisplayName("Should return status code 200 when find a company by name")
	void testFindByName() throws Exception {
		when(companyService.findByName("Lorem ipsum dolor")).thenReturn(company);
		
		mockMvc.perform(get("/api/v1/name/Lorem ipsum dolor")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(company)));
	}

	@Test
	@DisplayName("Should return status code 200 when find a company by CNPJ")
	void testFindByCnpj() throws Exception {
		when(companyService.findByCnpj("23647496000195")).thenReturn(company);
		
		mockMvc.perform(get("/api/v1/cnpj/23647496000195")
				.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(company)));
	}
	
	@Test
	@DisplayName("Should return status code 404 when not find a company by id")
	void testNotFindById() throws Exception {
		when(companyService.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get("/api/v1/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Should return status code 201 when a valid company is saved")
	void testSave() throws Exception {
		when(companyService.save(any(Company.class))).thenReturn(company);
		
		mockMvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(company)))
                .andExpect(header().string("Location", "http://localhost/api/v1/1"));
	}
	
	@Test
	@DisplayName("Should return status code 400 when a invalid company is provided")
	void testNotSave() throws Exception {		
		mockMvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Company())))
                .andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should return status code 200 when a valid company is updated")
	void testUpdate() throws Exception {
		when(companyService.update(any(Company.class))).thenReturn(company);
		
		mockMvc.perform(put("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(company)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(company)));
	}
	
	@Test
	@DisplayName("Should return status code 400 when a invalid company is updated")
	void testNotUpdate() throws Exception {		
		mockMvc.perform(put("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new Company())))
                .andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should return status code 200 when a company is deleted")
	void testDelete() throws Exception {
		doNothing().when(companyService).delete(any(Company.class));
		
        mockMvc.perform(delete("/api/v1")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(company)))
                .andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Should return status code 400 when try to delete a invalid company")
	void testNotDelete() throws Exception {
        mockMvc.perform(delete("/api/v1")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(new Company())))
                .andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Should return status code 200 and all saved companies")
	void testFindAll() throws Exception {
		when(companyService.findAll(any(Pageable.class))).thenReturn(companies);
		
        mockMvc.perform(get("/api/v1")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(mapper.writeValueAsString(companies)))
        		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
	}

}
