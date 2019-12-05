package com.watchit.watchsellers.repositories;

import com.watchit.watchsellers.dbobjects.Watch;
import com.watchit.watchsellers.dtos.WatchDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


/**
 * Mongo Repository that exposes query methods to interact with the mongo db
 */
public interface WatchRepository extends MongoRepository<Watch, Integer> {


    List<Watch> findAllByCaseShapeAndCaseBackAndDialIndexesAndDialColorAndMovementTime(String caseShape, String caseBack, String dialIndex, String dialColor, String movementTime);

    List<Watch> findAllByCaseShapeAndCaseBackAndDialIndexesAndDialColor(String caseShape, String caseBack, String dialIndex, String dialColor);

    List<Watch> findAllByCaseShapeAndCaseBackAndDialIndexes(String caseShape, String caseBack, String dialIndex);

    List<Watch> findAllByCaseShapeAndCaseBack(String caseShape, String caseBack);

    List<Watch> findAllByCaseShape(String caseShape);

    List<Watch> findAllByCaseShapeOrCaseBackOrDialIndexesOrDialColorOrMovementTime(String caseShape, String caseBack, String dialIndex, String dialColor, String movementTime);


}
