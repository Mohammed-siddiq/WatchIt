package com.watchit.watchsellers;

import com.watchit.watchsellers.dbobjects.Watch;
import com.watchit.watchsellers.dtos.FileStorageProperties;
import com.watchit.watchsellers.repositories.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class Application {
    @Autowired
    WatchRepository watchRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


    }

//    @Override
//    public void run(String... args) throws Exception {
//
//        Watch watch = new Watch();
//        watch.setWatchName("Test watch");
//        watch.setWatchBrand("Test brand");
//        watch.setWatchImage("demo Link");
////        watchRepository.insert(watch);
//        watchRepository.save(watch);
//
//    }
}