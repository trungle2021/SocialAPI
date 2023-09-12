package com.social.server.services;

import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
    private  final  String bucketName = "social-images-bucket";
    private final S3Client s3Client;

    public String uploadFile(MultipartFile uploadFile) {
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        String filePath = getPathFromMultipartFile(uploadFile);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("image/jpg")
                .contentDisposition("inline; filename=filename.jpg")
                .acl("public-read")
                .build();

       s3Client.putObject(putObjectRequest, Paths.get(filePath));
      return getURL(s3Client,bucketName, fileName);
    }

    public byte[] downloadFile(String fileName) {

        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        //get default download directory
       String path = System.getProperty("user.home") + "\\Downloads"+fileName;
       return getObjectBytes(s3Client,objectRequest,path);
    }

    public byte[] getObjectBytes(S3Client s3,GetObjectRequest objectRequest, String path){
        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(objectRequest);
        byte[] data = objectBytes.asByteArray();
        // Write the data to a local file.
        File myFile = new File(path);
        try {
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(S3Exception e){
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return data;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(builder -> {
            builder.bucket(bucketName);
            builder.key(fileName);
        });
        return "File deleted: " + fileName;
    }

   public String getPathFromMultipartFile(MultipartFile file){
       String fileName = file.getOriginalFilename();
       // Get the path of the temporary file
       File tempFile = null;
       try {
           Assert.notNull(fileName);
           tempFile = File.createTempFile(fileName, "");
           file.transferTo(tempFile);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
      return tempFile.getAbsolutePath();
   }



    public static String getURL(S3Client s3, String bucketName, String keyName ) {

        try {
            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(bucketName)
                    .key(keyName)
                    .build();

            URL url = s3.utilities().getUrl(request);
            return url.toExternalForm();

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return "Cannot generate URL for " + keyName;
    }
}

//    public String generatePresignedUploadUrl(String bucketName, String key){
//        GetObjectRequest getObjectRequest =
//
//    }

//    private File convertMultipartFileToFile(MultipartFile uploadFile) {
//        File convertFile = new File(Objects.requireNonNull(uploadFile.getOriginalFilename()));
//        try (FileOutputStream out = new FileOutputStream(convertFile)) {
//            out.write(uploadFile.getBytes());
//        } catch (IOException e) {
//            log.error("Error converting multipart file to file: ", e);
//        }
//        return convertFile;
//
//    }
