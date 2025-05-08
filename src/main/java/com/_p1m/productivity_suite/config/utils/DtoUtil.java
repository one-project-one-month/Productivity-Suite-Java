package com._p1m.productivity_suite.config.utils;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for DTO mapping operations.
 */
@Service
public class DtoUtil {

    /**
     * Maps a list of entities to a list of DTOs using ModelMapper.
     *
     * Example Usage:
     * <pre>
     * {@code
     * List<User> users = userRepository.findAll();
     * List<UserDto> userDtos = DtoUtil.mapList(users, UserDto.class, modelMapper);
     * </pre>
     *
     * @param entityList  the list of entities to be mapped
     * @param dtoClass    the class of the DTO
     * @param modelMapper the ModelMapper instance
     * @param <E>         the type of the entity
     * @param <D>         the type of the DTO
     * @return the list of mapped DTOs
     */
    public static <E, D> List<D> mapList(final List<E> entityList, final Class<D> dtoClass, final ModelMapper modelMapper) {
        return entityList.stream()
                .map(entity -> modelMapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * Maps an entity to a DTO using ModelMapper.
     *
     * Example Usage:
     * <pre>
     * {@code
     * User user = userRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("User not found"));
     * UserDto userDto = DtoUtil.map(user, UserDto.class, modelMapper);
     * </pre>
     *
     * @param source            the source entity to be mapped
     * @param destinationClass the class of the destination DTO
     * @param modelMapper      the ModelMapper instance
     * @param <E>               the type of the source entity
     * @param <D>               the type of the destination DTO
     * @return the mapped DTO
     */
    public static <E, D> D map(final E source, final Class<D> destinationClass, final ModelMapper modelMapper) {
        return modelMapper.map(source, destinationClass);
    }
}