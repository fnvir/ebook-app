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
import com.app.ebook.exceptions.StorageServiceException;
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
            throw new StorageServiceException("Could not create the directory where the uploaded files will be stored.", e);
        }
    }
    
    public String storeFile(MultipartFile file) {
    	return storeFile(file, file.getOriginalFilename());
    }
    
    public String storeFile(MultipartFile file, String filename) {
    	validateFile(file);
    	String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = StringUtils.cleanPath(filename)+(fileExtension==null||fileExtension.isBlank()?".bin":"."+fileExtension);
		Path destinationFile = storageLocation.resolve(fileName)
				.normalize().toAbsolutePath();
		if (!destinationFile.getParent().equals(storageLocation.toAbsolutePath())) {
			throw new StorageServiceException("Cannot store file outside current directory.");
		}
        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new StorageServiceException("Could not store file " + fileName + ". Please try again!", ex);
        }
        return "/content/"+fileName;
    }
    
    public String storeUserContent(String username, String newFilename, MultipartFile file) {
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
	            throw new StorageServiceException("Could not create the directory where the uploaded files will be stored.", e);
	        }
        }
    	Path destinationFile = userDir.resolve(filename).normalize().toAbsolutePath();
    	try {
    		Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
    		return "/content/"+username+"/"+filename;
    	} catch (IOException ex) {
    		throw new StorageServiceException("Could not store file " + filename + ". Please try again!", ex);
    	}
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) 
            throw new StorageServiceException("Cannot upload empty file");
        
        if (file.getSize() > properties.getMaxSize()) 
            throw new StorageServiceException("File size exceeds the limit of " + properties.getMaxSize() + " bytes.");
        
        String fileType = file.getContentType();
        if (!properties.getAllowedTypes().contains(fileType)) {
            throw new StorageServiceException("File type " + fileType + " is not allowed.");
        }
    }

    public Path loadFile(String username,String fileName) {
        return storageLocation.resolve(username).resolve(fileName).normalize();
    }
    
	public Resource loadAsResource(String username,String filename) {
		try {
			Path file = loadFile(username,filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable())
				return resource;
			else 
				throw new StorageFileNotFoundException("Resource doesn't exist on server: " + filename);
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Invalid resouce: " + filename, e);
		}
	}
	
}
