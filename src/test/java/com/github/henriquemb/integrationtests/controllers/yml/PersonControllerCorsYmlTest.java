package com.github.henriquemb.integrationtests.controllers.yml;

import com.github.henriquemb.config.TestConfigs;
import com.github.henriquemb.integrationtests.controllers.MockControllerPerson;
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

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerCorsYmlTest extends AbstractIntegrationTest {
	private static final String BASE_PATH = "/api/v1/person";
	private static final String INVALID_CORS_REQUEST = "Invalid CORS request";
	
	private static final String MEDIA_TYPE = MediaType.APPLICATION_YAML_VALUE;

	private static final RequestSpecification specificationErudio = new RequestSpecBuilder()
			.addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
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
		// Seta ID padrão
		mockedPerson.getPerson().setId(0L);
	}

	@Test
	@Order(1)
	void create() {
		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.body(getYmlBody())
				.when().post()
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(2)
	void findById() {
		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().get("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(3)
	void update() {
		mockedPerson.updateMockPerson();

		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.body(getYmlBody())
				.when().put("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(4)
	void disable() {
		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().patch("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(5)
	void delete() {
		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.pathParam("id", mockedPerson.getPerson().getId())
				.when().delete("{id}")
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	@Test
	@Order(6)
	void findAll() {
		String content = given(specificationErudio)
				.contentType(MEDIA_TYPE)
				.accept(MEDIA_TYPE)
				.when().get()
				.then().statusCode(403)
				.extract().body().asString();

		assertEquals(INVALID_CORS_REQUEST, content);
	}

	private String getYmlBody() {
		return objectMapper.writeValueAsString(mockedPerson.getPerson());
	}
}