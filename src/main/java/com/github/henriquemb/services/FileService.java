package com.github.henriquemb.services;

import com.github.henriquemb.config.FileStorageConfig;
import com.github.henriquemb.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
public class FileService {
	private final Path fileStorageLocation;

	@Autowired
	public FileService(FileStorageConfig config) {
		this.fileStorageLocation = config.getUploadPath();

		try {
			Files.createDirectories(this.fileStorageLocation);
		}
		catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

		try {
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation);

			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
		}
	}
}
