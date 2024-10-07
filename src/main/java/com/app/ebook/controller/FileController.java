package com.app.ebook.controller;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.ebook.services.FileStorageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {
	
	private final FileStorageService storageService;
	
	@GetMapping("/content/{username}/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String username, @PathVariable String filename) throws IOException {

		Resource file = storageService.loadAsResource(username,filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(Files.probeContentType(file.getFile().toPath())))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	
	@GetMapping("/content/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException{
		return serveFile("", filename);
	}

}
