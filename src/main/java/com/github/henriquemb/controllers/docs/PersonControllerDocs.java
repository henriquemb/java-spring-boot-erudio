package com.github.henriquemb.controllers.docs;

import com.github.henriquemb.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PersonControllerDocs {
	@Operation(
			summary = "Find all people",
			description = "Find all people",
			tags = "People",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(
													schema = @Schema(implementation = PersonDTO.class)
											)
									)
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	List<PersonDTO> findAll();

	@Operation(
			summary = "Find a person",
			description = "Find a specific person by your id",
			tags = "People",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = PersonDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	PersonDTO findById(@PathVariable long id);

	@Operation(
			summary = "Create a person",
			description = "Create a person",
			tags = "People",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = PersonDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	PersonDTO create(@RequestBody PersonDTO person);

	@Operation(
			summary = "Update a person",
			description = "Update a specific person by your id",
			tags = "People",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = PersonDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	PersonDTO update(@PathVariable long id, @RequestBody PersonDTO person);

	@Operation(
			summary = "Delete a person",
			description = "Delete a specific person by your id",
			tags = "People",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = PersonDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	ResponseEntity<Void> delete(@PathVariable long id);
}
