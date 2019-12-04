package com.watchit.watchsellers.controller;

import com.watchit.watchsellers.dbobjects.Watch;
import com.watchit.watchsellers.dtos.WatchDto;
import com.watchit.watchsellers.repositories.WatchRepository;
import com.watchit.watchsellers.servicelayer.WatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/watch")
public class WatchController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    WatchRepository watchRepository;
    @Autowired
    WatchService service;


    @PostMapping
    ResponseEntity<Object> addWatches(@RequestBody WatchDto watchDto) {

        try {
            logger.info("Received request to add {}", watchDto);
            WatchDto response = service.addWatch(watchDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            return exceptionHandler(ex);
        }
    }

    @GetMapping
    ResponseEntity<Object> getWatches() {
        try {
            return new ResponseEntity<>(service.getAllWatches(), HttpStatus.OK);
        } catch (Exception ex) {
            return exceptionHandler(ex);
        }
    }


    @PostMapping
    @RequestMapping("/similarWatches")
    ResponseEntity<Object> getSimilarWatches(@RequestBody WatchDto watchDto) {

        try {
            return new ResponseEntity<>(service.findSimilarWatches(watchDto), HttpStatus.OK);
        } catch (Exception ex) {
            return exceptionHandler(ex);
        }
    }

    private ResponseEntity<Object> exceptionHandler(Exception ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}


