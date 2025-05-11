package com._p1m.productivity_suite.config.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class EntityServiceHelper {

    /**
     * Maps a list of entities to a list of DTOs.
     */
    public static <E, D> List<D> mapList(List<E> entityList, Class<D> dtoClass, ModelMapper modelMapper) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Maps a single entity to a DTO.
     */
    public static <E, D> D map(E entity, Class<D> dtoClass, ModelMapper modelMapper) {
        return modelMapper.map(entity, dtoClass);
    }
}
