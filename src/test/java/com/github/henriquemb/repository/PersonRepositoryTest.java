package com.github.henriquemb.repository;

import com.github.henriquemb.integrationtests.testcontainers.AbstractIntegrationTest;
import com.github.henriquemb.model.Person;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonRepositoryTest extends AbstractIntegrationTest {
	@Autowired
	PersonRepository repository;

	private static Person person;

	@BeforeAll
	static void setUp() {
		person = new Person();
	}

	@Test
	@Order(1)
	void findPeopleByName() {
		Pageable pageable = PageRequest.of(0, 12, Sort.by(Sort.Direction.ASC, "firstName"));

		person = repository.findPeopleByName("iko", pageable).getContent().getFirst();

		assertNotNull(person);
		assertNotNull(person.getId());
		assertEquals("Smiljan - Croatia", person.getAddress());
	}

	@Test
	@Order(2)
	void disable() {
		Long personId = person.getId();
		repository.disable(personId);

		person = repository.findById(personId).orElse(null);

		assertNotNull(person);
		assertNotNull(person.getId());
		assertEquals("Smiljan - Croatia", person.getAddress());
		assertFalse(person.getEnabled());
	}
}