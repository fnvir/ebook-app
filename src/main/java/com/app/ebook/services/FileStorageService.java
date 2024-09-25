package com.app.ebook.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
    private final ResourceLoader resourceLoader;
    
    public FileStorageService(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
    }
	
    public String savePdfFile(String username, String bookTitle, MultipartFile file) throws IOException {
        Path staticFolderPath = Paths.get(resourceLoader.getResource("classpath:static").getURI());
        Path userFolderPath = staticFolderPath.resolve(username);
        if (!Files.exists(userFolderPath)) {
            Files.createDirectories(userFolderPath);
        }
        Path filePath = userFolderPath.resolve(bookTitle + ".pdf");
        file.transferTo(filePath.toFile());
        return  "/" + username + "/" + bookTitle + ".pdf";
    }

}
