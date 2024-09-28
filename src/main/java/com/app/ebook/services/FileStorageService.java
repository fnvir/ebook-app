package com.app.ebook.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.ebook.config.FileStorageProps;
import com.app.ebook.exceptions.FileStorageException;
import com.app.ebook.exceptions.StorageFileNotFoundException;

@Service
public class FileStorageService {
	
    private final Path storageLocation;
    private final FileStorageProps properties;
    
    public FileStorageService(FileStorageProps properties) {
        this.properties = properties;
        this.storageLocation = Path.of(properties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.storageLocation);
        } catch (Exception e) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }
    
    public String storeFile(MultipartFile file) {
        validateFile(file);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path destinationFile = storageLocation.resolve(fileName)
				.normalize().toAbsolutePath();
		if (!destinationFile.getParent().equals(storageLocation.toAbsolutePath())) {
			throw new FileStorageException(	"Cannot store file outside current directory.");
		}
        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    public String storeFile(String username, String newFilename, MultipartFile file) {
    	validateFile(file);
    	String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
    	String fileExtension = StringUtils.getFilenameExtension(originalFileName);
    	String sanitizedTitle=newFilename.replaceAll("[^a-zA-Z0-9\\-]", "_");
    	String filename = sanitizedTitle+(fileExtension==null||fileExtension.isBlank()?".bin":"."+fileExtension);
    	Path userDir = storageLocation.resolve(username);
        if (!Files.exists(userDir)) {
            try {
        		Files.createDirectories(userDir);
	        } catch (IOException e) {
	            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", e);
	        }
        }
    	Path destinationFile = userDir.resolve(filename).normalize().toAbsolutePath();
    	try {
    		Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
    		return "/"+username+"/"+filename;
    	} catch (IOException ex) {
    		throw new FileStorageException("Could not store file " + filename + ". Please try again!", ex);
    	}
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) 
            throw new FileStorageException("Cannot upload empty file");
        
        if (file.getSize() > properties.getMaxSize()) 
            throw new FileStorageException("File size exceeds the limit of " + properties.getMaxSize() + " bytes.");
        
        String fileType = file.getContentType();
        if (!properties.getAllowedTypes().contains(fileType)) {
            throw new FileStorageException("File type " + fileType + " is not allowed.");
        }
    }

    public Path loadFile(String fileName) {
        return storageLocation.resolve(fileName).normalize();
    }
    
	public Resource loadAsResource(String filename) {
		try {
			Path file = loadFile(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
				return resource;
			else 
				throw new StorageFileNotFoundException("Could not read file: " + filename);
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}
	
}
