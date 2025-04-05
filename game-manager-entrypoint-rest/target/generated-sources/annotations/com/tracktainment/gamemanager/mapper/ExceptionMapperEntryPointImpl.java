package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.exception.BusinessException;
import com.tracktainment.gamemanager.exception.ExceptionDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-05T00:37:24+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class ExceptionMapperEntryPointImpl implements ExceptionMapperEntryPoint {

    @Override
    public ExceptionDto toExceptionDto(BusinessException e) {
        if ( e == null ) {
            return null;
        }

        ExceptionDto.ExceptionDtoBuilder exceptionDto = ExceptionDto.builder();

        exceptionDto.code( e.getCode() );
        exceptionDto.httpStatusCode( e.getHttpStatusCode() );
        exceptionDto.reason( e.getReason() );
        exceptionDto.message( e.getMessage() );

        return exceptionDto.build();
    }
}
