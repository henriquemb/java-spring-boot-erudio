package com.github.henriquemb.controllers;

import com.github.henriquemb.controllers.docs.BookControllerDocs;
import com.github.henriquemb.data.dto.BookDTO;
import com.github.henriquemb.services.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Books", description = "Endpoints for Managing Books")
public class BookController implements BookControllerDocs {
	private final BookService service;

	public BookController(BookService service) {
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
	public List<BookDTO> findAll() {
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
	public BookDTO findById(@PathVariable long id) {
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
	public BookDTO create(@RequestBody BookDTO book) {
		return service.create(book);
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
	public BookDTO update(@PathVariable long id, @RequestBody BookDTO book) {
		return service.update(id, book);
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
