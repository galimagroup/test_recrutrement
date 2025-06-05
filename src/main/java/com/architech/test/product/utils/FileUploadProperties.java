package com.architech.test.product.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "file.upload")
@Getter
@Setter
public class FileUploadProperties {
    private String directory;
    private long maxSize;
    private List<String> allowedTypes;
}
