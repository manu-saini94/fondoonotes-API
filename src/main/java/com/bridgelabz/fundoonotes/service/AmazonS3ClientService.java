package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;

public interface AmazonS3ClientService {

	String uploadFileToS3Bucket(MultipartFile multipartFile, String jwt) throws S3BucketException;

	boolean deleteFileFromS3Bucket(String fileName) throws S3BucketException;

	String getFileFromS3Bucket(String jwt) throws S3BucketException;
}
