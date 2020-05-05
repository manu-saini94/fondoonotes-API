//package com.bridgelabz.fundoonotes.serviceimpl;
//
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
//import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;
//import com.bridgelabz.fundoonotes.model.Users;
//import com.bridgelabz.fundoonotes.repository.UserRepository;
//import com.bridgelabz.fundoonotes.service.AmazonS3ClientService;
//import com.bridgelabz.fundoonotes.utility.Utility;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.regions.Region;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.time.LocalDateTime;
//
//@Component
//public class AmazonS3ClientServiceImpl implements AmazonS3ClientService {
//
//	private String awsS3AudioBucket;
//	private AmazonS3 amazonS3;
//	private AmazonS3Client s3;
//	private String awsKeyId;
//	private Region region;
//
//	@Autowired
//	private Utility utility;
//
//	@Autowired
//	UserRepository userRepository;
//
//	// private static final Logger logger =
//	// LoggerFactory.getLogger(AmazonS3ClientServiceImpl.class);
//
//	@Autowired
//	public AmazonS3ClientServiceImpl(Region awsRegion, AWSCredentialsProvider awsCredentialsProvider,
//			String awsS3AudioBucket, String awsKeyId) {
//		this.amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsProvider)
//				.withRegion(awsRegion.getName()).build();
//		this.awsS3AudioBucket = awsS3AudioBucket;
//		this.awsKeyId = awsKeyId;
//		this.region = awsRegion;
//
//	}
//
//	@Async
//	public String uploadFileToS3Bucket(MultipartFile multipartFile, String jwt) throws S3BucketException {
//
//		String fileName = multipartFile.getOriginalFilename();
//		try {
//			try {
//				utility.validateToken(jwt);
//			} catch (Exception ex) {
//				throw new JWTTokenException(HttpStatus.FORBIDDEN,"Token not Found Exception",LocalDateTime.now());
//			}
//			// creating the file in the server (temporarily)
//			File file = new File(fileName);
//			FileOutputStream fos = new FileOutputStream(file);
//			fos.write(multipartFile.getBytes());
//			fos.close();
//			PutObjectRequest putObjectRequest = new PutObjectRequest(this.awsS3AudioBucket, fileName, file);
//			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
//			this.amazonS3.putObject(putObjectRequest);
//			System.out.println("Bef");
//			String url = "https://" + this.awsS3AudioBucket + ".s3." + region + ".amazonaws.com/" + fileName;
//			Users user = utility.getUser(jwt);
//			user.setProfilePicUrl(url);
//			userRepository.save(user);
//			file.delete();
//			return url;
//		} catch (Exception ex) {
//			throw new S3BucketException("Image already exists with same name", HttpStatus.NOT_ACCEPTABLE);
//		}
//
//	}
//
//	@Async
//	public String getFileFromS3Bucket(String jwt) throws S3BucketException {
//		try {
//			try {
//				utility.validateToken(jwt);
//			} catch (Exception ex) {
//				throw new JWTTokenException(HttpStatus.FORBIDDEN,"Token not Found Exception",LocalDateTime.now());
//			}
//			Users user = utility.getUser(jwt);
//			String url = user.getProfilePicUrl();
//			return url;
//		} catch (Exception ex) {
//			throw new S3BucketException("Image already exists with same name", HttpStatus.NOT_ACCEPTABLE);
//		}
//
//	}
//
//	@Async
//	public boolean deleteFileFromS3Bucket(String jwt) throws S3BucketException {
//
//		try {
//			try {
//				utility.validateToken(jwt);
//			} catch (Exception ex) {
//				throw new JWTTokenException(HttpStatus.FORBIDDEN,"Token not Found Exception",LocalDateTime.now());
//			}
//			Users user = utility.getUser(jwt);
//			String url = user.getProfilePicUrl();
//			String[] urlarr1 = url.split(".amazonaws.com/");
//			String fileName = urlarr1[1];
//			amazonS3.deleteObject(new DeleteObjectRequest(awsS3AudioBucket, fileName));
//			user.setProfilePicUrl(null);
//			if (userRepository.save(user) != null)
//				return true;
//			else
//				return false;
//
//		} catch (Exception ex) {
//			throw new S3BucketException("Image not found", HttpStatus.NOT_FOUND);
//		}
//
//	}
//
//}
