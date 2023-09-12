package com.social.server.utils;

import org.modelmapper.ModelMapper;

public class EntityMapper {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static <S,D> D mapToDto(S source, Class<D> destinationClass){
        return modelMapper.map(source,destinationClass);
    }

    public static <S,D> D mapToEntity(S source, Class<D> destinationClass){
        return modelMapper.map(source,destinationClass);
    }
}
