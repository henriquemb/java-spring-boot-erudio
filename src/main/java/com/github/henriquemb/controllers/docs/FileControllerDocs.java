package com.github.henriquemb.controllers.docs;

import com.github.henriquemb.data.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Files")
public interface FileControllerDocs {
	@Operation(
			summary = "Upload a file",
			description = "Upload a file to the server",
			tags = "Files",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = UploadFileResponseDTO.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	UploadFileResponseDTO uploadFile(MultipartFile file);

	@Operation(
			summary = "Upload a file list",
			description = "Upload a file list to the server",
			tags = "Files",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(
											mediaType = MediaType.APPLICATION_JSON_VALUE,
											array = @ArraySchema(
													schema = @Schema(implementation = UploadFileResponseDTO.class)
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
	List<UploadFileResponseDTO> uploadFiles(MultipartFile[] files);

	@Operation(
			summary = "Download a file",
			description = "Download a file from the server",
			tags = "Files",
			responses = {
					@ApiResponse(
							description = "Success",
							responseCode = "200",
							content = {
									@Content(schema = @Schema(implementation = Resource.class))
							}
					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal server error", responseCode = "500", content = @Content)
			}
	)
	ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
