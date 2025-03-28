package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.exception.BusinessException;
import com.tracktainment.gamemanager.exception.ExceptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ExceptionMapperEntryPoint {

    ExceptionDto toExceptionDto(BusinessException e);
}
