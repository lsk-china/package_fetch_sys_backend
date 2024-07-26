package com.lsk.packagefetch.helper;

import com.lsk.packagefetch.util.StatusCode;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class MinioHelper {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucketName}")
    private String bucketName;

    private static final Map<String, String> SUPPORTED_MIMETYPES = new HashMap<String, String>() {{
        put("image/jpeg", ".jpg");
        put("image/png", ".png");
        put("image/gif", ".gif");
    }};

    public String putObject(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        if (! SUPPORTED_MIMETYPES.containsKey(contentType)) {
            throw new StatusCode(406, "Unsupported content type: " + contentType);
        }
        String objectName = UUID.randomUUID().toString() + SUPPORTED_MIMETYPES.get(contentType);
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .object(objectName)
                            .bucket(bucketName)
                            .contentType(contentType)
                            .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                            .build()
            );
        } catch (Exception e) {
            throw new StatusCode(500, "Cannot upload file", e);
        }
        return objectName;
    }

    public org.springframework.core.io.Resource getObject(String objectName) {
        try {
            GetObjectResponse response = minioClient.getObject(GetObjectArgs.builder()
                    .object(objectName)
                    .bucket(bucketName)
                    .build());
            ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            while (response.available() > 0) {
                int len = response.read(buffer);
                os.write(buffer, 0, len);
            }
            org.springframework.core.io.Resource resource = new ByteArrayResource(os.toByteArray());
            return resource;
        } catch (Exception e) {
            throw new StatusCode(500, "cannot download file", e);
        }
    }

}
