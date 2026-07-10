package com.github.henriquemb.integrationtests.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.henriquemb.config.TestConfigs;
import com.github.henriquemb.integrationtests.dto.PersonDTO;
import com.github.henriquemb.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest extends AbstractIntegrationTest {
	private static final String INVALID_CORS_REQUEST = "Invalid CORS request";
	private static final String BASE_PATH = "/api/v1/person";

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;

	private static PersonDTO person;

	@BeforeAll
	static void setUp() {
		objectMapper = new ObjectMapper();
		// Ignora os links HATEOAS
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		person = new PersonDTO();
	}

	@Test
	@Order(1)
	void create() throws JsonProcessingException {
		mockPerson();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.body(person)
				.when().post()
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);

		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());

		assertTrue(createdPerson.getId() > 0);

		person.setId(createdPerson.getId());
		assertEquals(person, createdPerson);

		person = createdPerson;
	}

	@Test
	@Order(2)
	void createWithWrongOrigin() {
		mockPerson();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.body(person)
				.when().post()
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(3)
	void findById() throws JsonProcessingException {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.when().get("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(person, createdPerson);
	}

	@Test
	@Order(3)
	void findByIdWithWrongOrigin() {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.when().get("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(4)
	void update() throws JsonProcessingException {
		updateMockPerson();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.body(person)
				.when().put("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO updatedPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(person, updatedPerson);
	}

	@Test
	@Order(5)
	void updateWithWrongOrigin() {
		updateMockPerson();

		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.body(person)
				.when().put("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(6)
	void delete() {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.when().delete("{id}")
				.then().statusCode(204)
				.extract().body().asString();

		assertTrue(content.isEmpty());
	}

	@Test
	@Order(7)
	void deleteWithWrongOrigin() {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.pathParam("id", person.getId())
				.when().delete("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(8)
	void findAll() throws JsonProcessingException {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.when().get()
				.then().statusCode(200)
				.extract().body().asString();

		List<PersonDTO> updatedPerson = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonDTO.class));

		assertNotNull(updatedPerson);
		assertEquals(9, updatedPerson.size());
	}

	@Test
	@Order(9)
	void findAllWithWrongOrigin() {
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
				.setBasePath(BASE_PATH)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();

		String content = given(specification)
				.contentType(ContentType.JSON)
				.when().get()
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	private void mockPerson() {
		person.setFirstName("Richard");
		person.setLastName("Stallman");
		person.setAddress("New York City - New York - USA");
		person.setGender("Male");
	}

	private void updateMockPerson() {
		person.setFirstName("Moacir");
		person.setLastName("Baruffi");
	}
}