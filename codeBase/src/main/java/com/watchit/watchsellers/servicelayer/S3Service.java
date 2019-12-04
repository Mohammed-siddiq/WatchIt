package com.watchit.watchsellers.servicelayer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.AmazonServiceException;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * S3 service that takes care of all the interaction with the amazon s3 storage.
 * Any interaction that happens with S3 should go through this service
 *
 * @author Mohammed Siddiq
 */
@Service
public class S3Service {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * Returns list of objects (/files) under a s3 bucket
     *
     * @param bucketName the bucket name
     * @return {@link List<String>} having the objects under the bucket
     */

    public List<String> listObjects(String bucketName) {

        logger.info("Objects in S3 bucket {}", bucketName);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();
        ListObjectsV2Result result = s3.listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        List<String> s3Objects = new ArrayList<>();
        for (S3ObjectSummary os : objects) {
            logger.info("* " + os.getKey());
            s3Objects.add(os.getKey());
        }

        return s3Objects;

    }

    /**
     * Uploads an object to s3 bucket
     * @param filePath
     * @param bucketName
     * @return
     */
    public Optional<String> uploadObject(String filePath, String bucketName) {
        logger.info("Uploading {} to S3 bucket {}...\n", filePath, bucketName);
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();

        String keyName = filePath.split("//")[1];
        try {
            s3.putObject(bucketName, keyName, new File(filePath));
            return Optional.of(keyName);
        } catch (AmazonServiceException e) {
            logger.info(e.getErrorMessage());
            return Optional.empty();
        }
    }






}
