package com.watchit.watchsellers.repositories;

import com.watchit.watchsellers.dbobjects.Watch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WatchRepository extends MongoRepository<Watch, Integer> {
}
