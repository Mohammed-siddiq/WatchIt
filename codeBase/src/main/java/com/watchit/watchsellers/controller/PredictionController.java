package com.watchit.watchsellers.controller;


import com.google.cloud.automl.v1beta1.PredictResponse;
import com.watchit.watchsellers.configs.ConfigReader;
import com.watchit.watchsellers.dtos.WatchDto;
import com.watchit.watchsellers.servicelayer.FileStorageService;
import com.watchit.watchsellers.servicelayer.PredictionService;
import com.watchit.watchsellers.servicelayer.WatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * Api exposed for uploading an image
 *
 * @author mohammedsiddiq
 */
@RestController
@RequestMapping("/Predict")
@CrossOrigin
public class PredictionController {

    private static final Logger logger = LoggerFactory.getLogger(PredictionController.class);


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private WatchService watchService;

    @PostMapping("/findSimilar")
    public ResponseEntity<Object> similarWatches(@RequestParam("file") MultipartFile file) {


        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

//        PredictResponse predictResponse = predictionService.predict(ConfigReader.PROJECT_ID, "us-central1", ConfigReader.MODEL_ID, ConfigReader.UPLOAD_PATH + fileName, "0.3");


        List<WatchDto> watches = watchService.getAllWatches();
//        List<WatchDto> watches = predictionService.similarWatches(predictResponse);

        return new ResponseEntity<>(watches.stream().limit(15), HttpStatus.OK);


    }
}
