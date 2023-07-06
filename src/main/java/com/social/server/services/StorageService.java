package com.social.server.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
    private final String bucketName = "social-images-bucket";
    private final AmazonS3 s3Client;

    public String uploadFile(MultipartFile uploadFile) {
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        File file = convertMultipartFileToFile(uploadFile);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        s3Client.putObject(putObjectRequest);
        file.delete();
        URL s3Url = s3Client.getUrl(bucketName, fileName);
        //return s3 url
        return s3Url.toExternalForm();
    }

    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            log.error("Cannot download file", e);
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return "File deleted: " + fileName;
    }

    private File convertMultipartFileToFile(MultipartFile uploadFile) {
        File convertFile = new File(Objects.requireNonNull(uploadFile.getOriginalFilename()));
        try (FileOutputStream out = new FileOutputStream(convertFile)) {
            out.write(uploadFile.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipart file to file: ", e);
        }
        return convertFile;

    }
}
