package com.github.henriquemb.integrationtests.controllers.yml;

import com.github.henriquemb.config.TestConfigs;
import com.github.henriquemb.integrationtests.controllers.MockControllerPerson;
import com.github.henriquemb.integrationtests.dto.PersonDTO;
import com.github.henriquemb.integrationtests.dto.wrapper.json.PersonDTOJsonWrapper;
import com.github.henriquemb.integrationtests.dto.wrapper.yml.PersonDTOYmlWrapper;
import com.github.henriquemb.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.dataformat.yaml.YAMLMapper;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerYmlTest extends AbstractIntegrationTest {
	private static final String BASE_PATH = "/api/v1/person";
	
	private static final String MEDIA_TYPE = MediaType.APPLICATION_YAML_VALUE;

	private static final RequestSpecification specification = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCALHOST)
			.setBasePath(BASE_PATH)
			.setPort(TestConfigs.SERVER_PORT)
			.addFilter(new RequestLoggingFilter(LogDetail.ALL))
			.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
			.setConfig(RestAssuredConfig.config()
					.encoderConfig(EncoderConfig.encoderConfig()
							.encodeContentTypeAs(MEDIA_TYPE, ContentType.TEXT)
					)
			)
			.build();
	private static final YAMLMapper objectMapper = YAMLMapper.builder()
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.build();

	private static final MockControllerPerson mockedPerson = new MockControllerPerson();

	@BeforeAll
	static void setUp() {
		// Ignora os links HATEOAS
	}

	@Test
	@Order(1)
	void create() {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.body(getYmlBody())
				.when().post()
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		mockedPerson.getPerson().setId(createdPerson.getId());
		assertEquals(mockedPerson.getPerson(), createdPerson);
	}

	@Test
	@Order(2)
	void findById() {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().get("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(mockedPerson.getPerson(), createdPerson);
	}

	@Test
	@Order(3)
	void update() {
		mockedPerson.updateMockPerson();

		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.body(getYmlBody())
				.when().put("{id}")
				.then().statusCode(200)
				.extract().body().asString();

		PersonDTO updatedPerson = objectMapper.readValue(content, PersonDTO.class);
		assertEquals(mockedPerson.getPerson(), updatedPerson);
	}

	@Test
	@Order(4)
	void disable() {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
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
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().delete("{id}")
				.then().statusCode(204)
				.extract().body().asString();

		assertTrue(content.isEmpty());
	}

	@Test
	@Order(6)
	void findAll() {
		String content = given(specification)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.when().get()
				.then().statusCode(200)
				.extract().body().asString();

		//List<PersonDTO> updatedPerson = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonDTO.class));
		PersonDTOYmlWrapper wrapper = objectMapper.readValue(content, PersonDTOYmlWrapper.class);
		List<PersonDTO> updatedPerson = wrapper.getContent();

		assertNotNull(updatedPerson);
		assertEquals(12, updatedPerson.size());
	}

	private String getYmlBody() {
		return objectMapper.writeValueAsString(mockedPerson.getPerson());
	}
}