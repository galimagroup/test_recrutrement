package com.shop.alten.test_technique.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;
import java.io.FileOutputStream;
import java.util.Base64;

@Service
public class ImageService {

    private static final String IMAGE_DIRECTORY = "C:\\wamp64\\www\\image_shop";

    public String saveBase64Image(String base64Image) throws Exception {
        String fileName = UUID.randomUUID().toString() + ".png";

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        File imageFile = new File(IMAGE_DIRECTORY, fileName);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageBytes);
        }

        return fileName;
    }
}
