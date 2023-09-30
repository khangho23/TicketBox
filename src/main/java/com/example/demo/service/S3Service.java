package com.example.demo.service;

import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;

@Service
public class S3Service {
    @Autowired
    private AmazonS3 amazonS3;

    public void saveFile(String bucketName, String key, InputStream inputStream, ObjectMetadata objectMetadata)
            throws IllegalStateException {
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, objectMetadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        } catch (AmazonServiceException amazonServiceException) {
            amazonServiceException.printStackTrace();
            throw new AmazonServiceException(amazonServiceException.getErrorMessage());
        }
    }

    public void deleteFile(String bucketName, String key) {
        try {
            amazonS3.deleteObject(bucketName, key);
        } catch (AmazonServiceException amazonServiceException) {
            amazonServiceException.printStackTrace();
            throw new AmazonServiceException(amazonServiceException.getErrorMessage());
        }
    }
}
