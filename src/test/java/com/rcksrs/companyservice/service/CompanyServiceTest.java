package com.rcksrs.companyservice.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.rcksrs.companyservice.domain.Address;
import com.rcksrs.companyservice.domain.Company;
import com.rcksrs.companyservice.domain.Contact;
import com.rcksrs.companyservice.domain.ContactType;
import com.rcksrs.companyservice.exception.BusinessException;
import com.rcksrs.companyservice.exception.ResourceNotFoundException;
import com.rcksrs.companyservice.repository.CompanyRepository;

@SpringBootTest
class CompanyServiceTest {
	
	@Mock
	private CompanyRepository companyRepository;
	
	@InjectMocks
	private CompanyService companyService;
	
	private static Company company;
	private static List<Company> companies;
	
	@BeforeAll
	static void beforeAll() {
		company = Company.builder()
				.id("1")
				.cnpj("23647496000195")
				.name("Lorem ipsum dolor")
				.address(new Address("02319160", "Brazil", "Maranhao", "Sao Luis", "Rua Cargo 977"))
				.contacts(List.of(new Contact("9827849063", ContactType.PHONE)))
				.build();
		
		companies = List.of(company);
	}

	@Test
	@DisplayName("Should find a company when a company id is provided")
	void testFindById() {
		when(companyRepository.findById("1")).thenReturn(Optional.of(company));
		
		var companySaved = companyService.findById("1");
		assertAll(() -> {
			assertEquals("23647496000195", companySaved.getCnpj());
			assertEquals("Lorem ipsum dolor", companySaved.getName());
			assertEquals(1, companySaved.getContacts().size());
			assertEquals("Sao Luis", companySaved.getAddress().getCity());
		});
	}

	@Test
	@DisplayName("Should find a company when a company CNPJ is provided")
	void testFindByCnpj() {
		when(companyRepository.findByCnpj("23647496000195")).thenReturn(Optional.of(company));
		
		var companySaved = companyService.findByCnpj("23647496000195");
		assertAll(() -> {
			assertEquals("23647496000195", companySaved.getCnpj());
			assertEquals("Lorem ipsum dolor", companySaved.getName());
			assertEquals(1, companySaved.getContacts().size());
			assertEquals("Sao Luis", companySaved.getAddress().getCity());
		});
	}

	@Test
	@DisplayName("Should find a company when a company name is provided")
	void testFindByName() {
		when(companyRepository.findByName("Lorem ipsum dolor")).thenReturn(Optional.of(company));
		
		var companySaved = companyService.findByName("Lorem ipsum dolor");
		assertAll(() -> {
			assertEquals("23647496000195", companySaved.getCnpj());
			assertEquals("Lorem ipsum dolor", companySaved.getName());
			assertEquals(1, companySaved.getContacts().size());
			assertEquals("Sao Luis", companySaved.getAddress().getCity());
		});
	}

	@Test
	@DisplayName("Should return all companies saved")
	void testFindAll() {
		when(companyRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(companies));
		
		var companiesSaved = companyService.findAll(Pageable.unpaged());
		assertEquals(1, companiesSaved.getContent().size());
	}

	@Test
	@DisplayName("Should save a company")
	void testSave() {
		when(companyRepository.save(any(Company.class))).thenReturn(company);
		when(companyRepository.findByCnpj(any(String.class))).thenReturn(Optional.empty());
		
		var newCompany = Company.builder()
				.name("Lorem ipsum dolor")
				.cnpj("23647496000195")
				.build();
		
		var companySaved = companyService.save(newCompany);
		assertAll(() -> {
			assertEquals(newCompany.getCnpj(), companySaved.getCnpj());
			assertEquals(newCompany.getName(), companySaved.getName());
		});
	}

	@Test
	@DisplayName("Should update a company")
	void testUpdate() {
		when(companyRepository.save(any(Company.class))).thenReturn(company);
		when(companyRepository.findByIdAndCnpj(any(String.class), any(String.class))).thenReturn(Optional.of(company));
		
		var newCompany = Company.builder()
				.id("1")
				.name("Lorem ipsum dolor")
				.cnpj("23647496000195")
				.build();
		
		var companySaved = companyService.update(newCompany);
		assertAll(() -> {
			assertEquals(newCompany.getId(), companySaved.getId());
			assertEquals(newCompany.getCnpj(), companySaved.getCnpj());
			assertEquals(newCompany.getName(), companySaved.getName());
		});
	}

	@Test
	@DisplayName("Should delete a company")
	void testDelete() {
		when(companyRepository.findById(any(String.class))).thenReturn(Optional.of(company));
		doNothing().when(companyRepository).deleteById("1");
		
		assertDoesNotThrow(() -> companyService.delete(company));
	}
	
	@Test
	@DisplayName("Should not find a company when a not existent company id is provided")
	void testNotFindById() {
		when(companyRepository.findById(any(String.class))).thenReturn(Optional.empty());		
		assertThrows(ResourceNotFoundException.class, () -> companyService.findById("1"));
	}

	@Test
	@DisplayName("Should not find a company when a not existent company CNPJ is provided")
	void testNotFindByCnpj() {
		when(companyRepository.findByCnpj(any(String.class))).thenReturn(Optional.empty());		
		assertThrows(ResourceNotFoundException.class, () -> companyService.findByCnpj("23647496000195"));
	}

	@Test
	@DisplayName("Should not find a company when a not existent company name is provided")
	void testNotFindByName() {
		when(companyRepository.findByName(any(String.class))).thenReturn(Optional.empty());		
		assertThrows(ResourceNotFoundException.class, () -> companyService.findByName("Lorem ipsum dolor"));
	}

	@Test
	@DisplayName("Should not save a company when CNPJ already exists")
	void testNotSave() {
		when(companyRepository.findByCnpj(any(String.class))).thenReturn(Optional.of(company));
		
		var newCompany = Company.builder()
				.name("Lorem ipsum dolor")
				.cnpj("23647496000195")
				.build();
		
		assertThrows(BusinessException.class, () -> companyService.save(newCompany));
	}

	@Test
	@DisplayName("Should not update a company when CNPJ changes")
	void testNotUpdate() {
		when(companyRepository.findByIdAndCnpj(any(String.class), any(String.class))).thenReturn(Optional.empty());
		
		var newCompany = Company.builder()
				.id("1")
				.name("Lorem ipsum dolor")
				.cnpj("23647496000195")
				.build();
		
		assertThrows(BusinessException.class, () -> companyService.update(newCompany));
	}

	@Test
	@DisplayName("Should not delete a company when company not exists")
	void testNotDelete() {
		when(companyRepository.findById(any(String.class))).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> companyService.delete(company));
	}

}
