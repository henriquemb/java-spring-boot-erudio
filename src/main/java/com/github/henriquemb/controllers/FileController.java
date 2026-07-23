package com.github.henriquemb.controllers;

import com.github.henriquemb.controllers.docs.FileControllerDocs;
import com.github.henriquemb.data.dto.UploadFileResponseDTO;
import com.github.henriquemb.services.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@Tag(name = "Files", description = "Endpoints for Managing Files")
public class FileController implements FileControllerDocs {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	private final FileService service;

	public FileController(FileService service) {
		this.service = service;
	}

	@Override
	@PostMapping("/uploadFile")
	public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
		logger.info("Received file upload request: {}", file.getOriginalFilename());
		String fileName = service.storeFile(file);
		return getUploadFileResponseByFile(fileName, file);
	}

	@Override
	@PostMapping("uploadFiles")
	public List<UploadFileResponseDTO> uploadFiles(@RequestParam("files") MultipartFile[] files) {
		logger.info("Received multiple file upload request: {} files", files.length);
		return Arrays.stream(files)
				.map(file -> {
					String fileName = service.storeFile(file);
					return getUploadFileResponseByFile(fileName, file);
				})
				.toList();
	}

	@Override
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		logger.info("Received file download request: {}", fileName);

		Resource resource = service.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		}
		catch (Exception e) {
			logger.error("Could not determine file type.", e);
		}
		
		if (contentType == null) {
			contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	private UploadFileResponseDTO getUploadFileResponseByFile(String fileName, MultipartFile file) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/api/v1/file/download/")
				.path(fileName)
				.toUriString();

		return new UploadFileResponseDTO(
				fileName,
				fileDownloadUri,
				file.getContentType(),
				file.getSize()
		);
	}
}
