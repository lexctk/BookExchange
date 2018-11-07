package com.bookexchange.util;

import java.io.InputStream;
import java.nio.file.Paths;

import javax.servlet.http.Part;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AmazonS3Util {

	/**
	 * Upload file to AmazonS3 and return public url
	 * 
	 * @param filePart from a MultipartConfig form
	 * @return public AmazonS3 url
	 */
	public static String awsUpload (Part filePart, String userID) {
		String avatar = "";
		
		try {
		    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		    
		    fileName = userID + getExtensionByStringHandling(fileName);
		    InputStream fileContent = filePart.getInputStream();
		    
		    //TODO restrict uploads to images only
		    ObjectMetadata md = new ObjectMetadata();
		    md.setContentLength(filePart.getSize());
		    md.setContentType(filePart.getContentType());
		    
		    BasicAWSCredentials creds = awsCredentials();
		    try {
		    	AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
		    			.withCredentials(new AWSStaticCredentialsProvider(creds))
		    			.withRegion(Regions.EU_WEST_3)
		    			.build();		    
		    	s3Client.putObject(new PutObjectRequest("lexctkbookexchange", fileName, fileContent, md));
		    	avatar = s3Client.getUrl("lexctkbookexchange", fileName).toString();
		    } catch (final AmazonServiceException ase) {
		    	System.out.println("Request made it to Amazon S3 but was rejected: " + ase.getMessage());
		    } catch (final AmazonClientException ace) {
		    	System.out.println("Client encountered error trying to communicate with Amazon S3: " + ace.getMessage());
		    }
		
		} catch (final Exception e) {
			System.out.println("Upload failed" + e.getMessage());
		}

		return avatar;
	}
	
	/**
	 * Generate AWS credentials from AWS access key and secret key
	 * 
	 * Look for keys in environment variables, and if not found, in config.properties file
	 * Key names must be AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY
	 * 
	 * @return AWS credentials
	 */
	private static BasicAWSCredentials awsCredentials() {
		String AWS_ACCESS_KEY_ID = System.getenv("AWS_ACCESS_KEY_ID");
		if (AWS_ACCESS_KEY_ID == null) AWS_ACCESS_KEY_ID = PropertiesUtil.getValue ("AWS_ACCESS_KEY_ID");
		
		String AWS_SECRET_ACCESS_KEY = System.getenv("AWS_SECRET_ACCESS_KEY");
		if (AWS_SECRET_ACCESS_KEY == null) AWS_SECRET_ACCESS_KEY = PropertiesUtil.getValue ("AWS_SECRET_ACCESS_KEY");	
	    
	    BasicAWSCredentials creds = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY); 
	    return creds;
	}
	
	private static String getExtensionByStringHandling(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			return fileName.substring(index);
		} else return "";
	}	
}
