package com.watchit.watchsellers.MapStruct;

import com.watchit.watchsellers.dbobjects.Watch;
import com.watchit.watchsellers.dtos.WatchDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface WatchMapper {

    WatchMapper INSTANCE = Mappers.getMapper(WatchMapper.class);

    WatchDto watchToWatchDto(Watch watch);

    Watch watchDtoToWatch(WatchDto watch);

    List<WatchDto> watchToDTOs(List<Watch> watches);

}
