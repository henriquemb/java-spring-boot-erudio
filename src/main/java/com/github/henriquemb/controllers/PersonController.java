package com.github.henriquemb.controllers;

import com.github.henriquemb.controllers.docs.PersonControllerDocs;
import com.github.henriquemb.data.dto.PersonDTO;
import com.github.henriquemb.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "People", description = "Endpoints for Managing People")
//@CrossOrigin(origins = { "http://localhost:8080" })
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
	public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "12") Integer size,
			@RequestParam(value = "direction", defaultValue = "asc") String direction,
			PagedResourcesAssembler<PersonDTO> assembler
	) {
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

		Page<PersonDTO> people = service.findAll(pageable);

		Link link = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(PersonController.class).findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString(),
						assembler
                )
        ).withSelfRel();

		return ResponseEntity.ok(assembler.toModel(people, link));
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
	//@CrossOrigin(origins = { "http://localhost:8080" })
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
	//@CrossOrigin(origins = { "http://localhost:8080" })
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
	@PatchMapping(
			value = "/{id}",
			produces = {
					MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_YAML_VALUE
			}
	)
	public PersonDTO disable(@PathVariable long id) {
		return service.disable(id);
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
