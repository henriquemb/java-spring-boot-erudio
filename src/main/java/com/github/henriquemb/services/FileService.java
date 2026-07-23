package com.github.henriquemb.services;

import com.github.henriquemb.config.FileStorageConfig;
import com.github.henriquemb.exception.FileNotFoundException;
import com.github.henriquemb.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	private final Path fileStorageLocation;

	@Autowired
	public FileService(FileStorageConfig config) {
		this.fileStorageLocation = config.getUploadPath();

		try {
			Files.createDirectories(this.fileStorageLocation);
			logger.info("Directory created successfully: {}", this.fileStorageLocation);
		}
		catch (Exception e) {
			logger.error("Could not create the directory where the uploaded files will be stored.", e);
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
		logger.info("Storing file: {}", fileName);

		try {
			if (fileName.contains("..")) {
				logger.error("Invalid path sequence in filename: {}", fileName);
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			logger.info("File stored successfully: {}", fileName);

			return fileName;
		} catch (Exception e) {
			logger.error("Could not store file {}. Please try again!", fileName, e);
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
		logger.info("Downloading file: {}", filePath);

		try {
			Resource resource = UrlResource.from(filePath.toUri());
			if (!resource.exists() || !resource.isReadable()) {
				logger.error("File not found or not readable: {}", filePath);
				throw new FileNotFoundException("File not found or not readable " + filePath);
			}

			return resource;
		}
		catch (Exception e) {
			logger.error("Could not download file {}. Please try again!", filePath, e);
			throw new FileStorageException("Could not download file " + filePath + ". Please try again!", e);
		}
	}
}
