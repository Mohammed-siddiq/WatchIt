package com.watchit.watchsellers.servicelayer;

import com.watchit.watchsellers.MapStruct.WatchMapper;
import com.watchit.watchsellers.dbobjects.Watch;
import com.watchit.watchsellers.dtos.WatchDto;
import com.watchit.watchsellers.repositories.WatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer
 *
 * @author mohammedsiddiq
 */

@Service
public class WatchService {

    Logger logger = LoggerFactory.getLogger(WatchService.class);
    @Autowired
    private WatchRepository watchRepository;

    /**
     * Adds the watch to the Database
     *
     * @param watchDto watch to be added
     * @return Added {@link WatchDto}
     */
    public WatchDto addWatch(WatchDto watchDto) {
        logger.info("Adding watch {}", watchDto);
        Watch entityToAdd = WatchMapper.INSTANCE.watchDtoToWatch(watchDto);
        return WatchMapper.INSTANCE.watchToWatchDto(watchRepository.save(entityToAdd));

    }

    /**
     * Returns the list of all watches added
     *
     * @return {@link List<WatchDto>} available in the database
     */
    public List<WatchDto> getAllWatches() {

        logger.info("Getting all watches");
        List<Watch> allWatches = watchRepository.findAll();
        logger.info("All watches : {}", allWatches);
        return WatchMapper.INSTANCE.watchToDTOs(allWatches);


    }


    /**
     * Finds similar watches from the catalog in db
     *
     * @param similarTo The watch similar to which recommendations are made
     * @return {@link List<WatchDto>} that are similar
     */
    public List<WatchDto> findSimilarWatches(WatchDto similarTo) {

        logger.info("Finding similar watches to {} ", similarTo);

        List<Watch> allWatches = new ArrayList<>();

        allWatches.addAll(watchRepository.findAllByCaseShapeOrCaseBackOrDialIndexesOrDialColorOrMovementTime(similarTo.getCaseShape(), similarTo.getCaseBack(), similarTo.getDialIndexes(), similarTo.getDialColor(), similarTo.getMovementTime()));

        return WatchMapper.INSTANCE.watchToDTOs(allWatches);


    }


}