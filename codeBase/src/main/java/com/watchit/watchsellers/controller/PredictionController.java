package com.watchit.watchsellers.controller;


import com.google.cloud.automl.v1beta1.PredictResponse;
import com.watchit.watchsellers.dtos.WatchDto;
import com.watchit.watchsellers.servicelayer.FileStorageService;
import com.watchit.watchsellers.servicelayer.PredictionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class PredictionController {

    private static final Logger logger = LoggerFactory.getLogger(PredictionController.class);


    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/findSimilar")
    public ResponseEntity<Object> similarWatches(@RequestParam("file") MultipartFile file) {


        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        PredictResponse predictResponse = predictionService.predict("pro-century-260801", "us-central1", "ICN5846081334652436480", "uploads/" + fileName, "0.3");


        List<WatchDto> watches = predictionService.similarWatches(predictResponse);

        return new ResponseEntity<>(watches, HttpStatus.OK);


    }
}