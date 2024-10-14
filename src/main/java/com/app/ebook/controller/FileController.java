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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Tag(name = "Files", description = "API for serving files")
public class FileController {

    private final FileStorageService storageService;

    @GetMapping("/content/{username}/{filename:.+}")
    @ResponseBody
    @Operation(summary = "Serve a user's file", description = "Serve a file uploaded by an user")
    @ApiResponse(responseCode = "200", description = "File served successfully")
    public ResponseEntity<Resource> serveFile(@PathVariable String username, @PathVariable String filename) throws IOException {

        Resource file = storageService.loadAsResource(username, filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(file.getFile().toPath())))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/content/{filename:.+}")
    @ResponseBody
    @Operation(summary = "Serve a public file", description = "Serve a public file")
    @ApiResponse(responseCode = "200", description = "Public file served successfully")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {
        return serveFile("", filename);
    }
}
