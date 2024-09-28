package com.app.ebook.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "storage")
public class FileStorageProps {
    private String uploadDir;
    private long maxSize;
    private List<String> allowedTypes;
}
