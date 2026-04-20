package com.github.henriquemb.controllers.docs;

import com.github.henriquemb.data.dto.BookDTO;
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

public interface BookControllerDocs {
	@Operation(
			summary = "Find all books",
			description = "Find all books",
			tags = "Books",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(
													schema = @Schema(implementation = BookDTO.class)
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
	List<BookDTO> findAll();

	@Operation(
			summary = "Find a book",
			description = "Find a specific book by your id",
			tags = "Books",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = BookDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	BookDTO findById(@PathVariable long id);

	@Operation(
			summary = "Create a book",
			description = "Create a book",
			tags = "Books",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = BookDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	BookDTO create(@RequestBody BookDTO book);

	@Operation(
			summary = "Update a book",
			description = "Update a specific book by your id",
			tags = "Books",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = BookDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	BookDTO update(@PathVariable long id, @RequestBody BookDTO book);

	@Operation(
			summary = "Delete a book",
			description = "Delete a specific book by your id",
			tags = "Books",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = BookDTO.class))
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
