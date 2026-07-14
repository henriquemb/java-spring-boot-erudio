package com.github.henriquemb.integrationtests.controllers.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.henriquemb.config.TestConfigs;
import com.github.henriquemb.integrationtests.controllers.MockControllerPerson;
import com.github.henriquemb.integrationtests.dto.PersonDTO;
import com.github.henriquemb.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {
	private static final String BASE_PATH = "/api/v1/person";
	
	private static final String MEDIA_TYPE = MediaType.APPLICATION_JSON_VALUE;

	private static final RequestSpecification specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
			.setBasePath(BASE_PATH)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.build();
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final MockControllerPerson mockedPerson = new MockControllerPerson();

	@BeforeAll
	static void setUp() {
		// Ignora os links HATEOAS
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@Test
	@Order(1)
	void create() throws JsonProcessingException {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.body(mockedPerson.getPerson())
				.when().post()
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		mockedPerson.getPerson().setId(createdPerson.getId());
		assertEquals(mockedPerson.getPerson(), createdPerson);
	}

	@Test
	@Order(2)
	void findById() throws JsonProcessingException {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().get("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(mockedPerson.getPerson(), createdPerson);
	}

	@Test
	@Order(3)
	void update() throws JsonProcessingException {
		mockedPerson.updateMockPerson();

		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.body(mockedPerson.getPerson())
				.when().put("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO updatedPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(mockedPerson.getPerson(), updatedPerson);
	}

	@Test
	@Order(4)
	void disable() throws JsonProcessingException {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().patch("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO updatedPerson = objectMapper.readValue(content, PersonDTO.class);

		mockedPerson.getPerson().setEnabled(false);

		assertFalse(updatedPerson.getEnabled());
		assertEquals(mockedPerson.getPerson(), updatedPerson);
	}

	@Test
	@Order(5)
	void delete() {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().delete("{id}")
				.then().statusCode(204)
				.extract().body().asString();

		assertTrue(content.isEmpty());
	}

	@Test
	@Order(6)
	@Disabled("REASON: Still under development")
	void findAll() throws JsonProcessingException {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.when().get()
				.then().statusCode(200)
				.extract().body().asString();

		List<PersonDTO> updatedPerson = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonDTO.class));

		assertNotNull(updatedPerson);
		assertEquals(9, updatedPerson.size());
	}
}