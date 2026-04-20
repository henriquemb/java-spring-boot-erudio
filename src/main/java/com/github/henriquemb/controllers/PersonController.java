package com.github.henriquemb.controllers;

import com.github.henriquemb.controllers.docs.PersonControllerDocs;
import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {
	private final PersonService service;

	public PersonController(PersonService service) {
		this.service = service;
	}

	@Override
	@GetMapping(
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			}
	)
	public List<PersonDTO> findAll() {
		return service.findAll();
	}

	@Override
	@GetMapping(
			value = "/{id}",
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			}
	)
	public PersonDTO findById(@PathVariable long id) {
		return service.findById(id);
	}

	@Override
	@PostMapping(
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			},
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			}
	)
	public PersonDTO create(@RequestBody PersonDTO person) {
		return service.create(person);
	}

	@Override
	@PutMapping(
			value = "/{id}",
			consumes = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			},
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			}
	)
	public PersonDTO update(@PathVariable long id, @RequestBody PersonDTO person) {
		return service.update(id, person);
	}

	@Override
	@DeleteMapping(
			value = "/{id}"
	)
	public ResponseEntity<Void> delete(@PathVariable long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
