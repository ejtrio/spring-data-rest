package com.ejtrio.springdatarest.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class JsonHelper {
    public static String getJsonStringFromFile(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }
}
