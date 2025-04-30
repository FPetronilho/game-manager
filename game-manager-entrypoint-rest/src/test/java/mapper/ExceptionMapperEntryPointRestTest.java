package mapper;


import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.exception.*;
import com.tracktainment.gamemanager.mapper.ExceptionMapperEntryPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExceptionMapperEntryPointRestTest {

    private final ExceptionMapperEntryPoint mapper = Mappers.getMapper(ExceptionMapperEntryPoint.class);

    @Test
    void shouldMapResourceNotFoundException() {
        // Arrange
        String resourceId = UUID.randomUUID().toString();
        ResourceNotFoundException exception = new ResourceNotFoundException(Game.class, resourceId);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getCode(), result.getCode());
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getReason(), result.getReason());
        assertEquals(String.format("Game %s not found.", resourceId), result.getMessage());
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapResourceAlreadyExistsException() {
        // Arrange
        String resourceId = UUID.randomUUID().toString();
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(Game.class, resourceId);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getCode(), result.getCode());
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getReason(), result.getReason());
        assertEquals(String.format("Game %s already exists.", resourceId), result.getMessage());
        assertEquals(HttpStatus.CONFLICT.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapAuthenticationFailedException() {
        // Arrange
        String errorMessage = "User not authenticated";
        AuthenticationFailedException exception = new AuthenticationFailedException(errorMessage);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getCode(), result.getCode());
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getReason(), result.getReason());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapAuthorizationFailedException() {
        // Arrange
        String errorMessage = "User not authorized";
        AuthorizationFailedException exception = new AuthorizationFailedException(errorMessage);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHORIZED.getCode(), result.getCode());
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHORIZED.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHORIZED.getReason(), result.getReason());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(HttpStatus.FORBIDDEN.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapParameterValidationFailedException() {
        // Arrange
        String errorMessage = "Invalid parameter values";
        ParameterValidationFailedException exception = new ParameterValidationFailedException(errorMessage);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.PARAMETER_VALIDATION_ERROR.getCode(), result.getCode());
        assertEquals(ExceptionCode.PARAMETER_VALIDATION_ERROR.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.PARAMETER_VALIDATION_ERROR.getReason(), result.getReason());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapInternalServerErrorException() {
        // Arrange
        String errorMessage = "Unexpected server error";
        InternalServerErrorException exception = new InternalServerErrorException(errorMessage);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getCode(), result.getCode());
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getReason(), result.getReason());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapGenericBusinessException() {
        // Arrange
        String errorMessage = "A custom business error";
        BusinessException exception = new BusinessException(ExceptionCode.CONFIGURATION_ERROR, errorMessage);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.CONFIGURATION_ERROR.getCode(), result.getCode());
        assertEquals(ExceptionCode.CONFIGURATION_ERROR.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.CONFIGURATION_ERROR.getReason(), result.getReason());
        assertEquals(errorMessage, result.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getHttpStatusCode());
    }

    @Test
    void shouldMapBusinessExceptionWithoutMessage() {
        // Arrange
        BusinessException exception = new BusinessException(ExceptionCode.INTERNAL_SERVER_ERROR);

        // Act
        ExceptionDto result = mapper.toExceptionDto(exception);

        // Assert
        assertNotNull(result);
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getCode(), result.getCode());
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatusCode(), result.getHttpStatusCode());
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getReason(), result.getReason());
        assertNull(result.getMessage());
    }

    @Test
    void shouldHandleNullInput() {
        // Act
        ExceptionDto result = mapper.toExceptionDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    void shouldMapAllBusinessExceptionTypes() {
        String resourceId = UUID.randomUUID().toString();
        String errorMessage = "Test error message";

        // ResourceNotFoundException
        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException(Game.class, resourceId);
        ExceptionDto resourceNotFoundDto = mapper.toExceptionDto(resourceNotFoundException);
        assertEquals(ExceptionCode.RESOURCE_NOT_FOUND.getCode(), resourceNotFoundDto.getCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), resourceNotFoundDto.getHttpStatusCode());

        // ResourceAlreadyExistsException
        ResourceAlreadyExistsException resourceAlreadyExistsException =
                new ResourceAlreadyExistsException(Game.class, resourceId);
        ExceptionDto resourceAlreadyExistsDto = mapper.toExceptionDto(resourceAlreadyExistsException);
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getCode(), resourceAlreadyExistsDto.getCode());
        assertEquals(HttpStatus.CONFLICT.value(), resourceAlreadyExistsDto.getHttpStatusCode());

        // AuthenticationFailedException
        AuthenticationFailedException authenticationFailedException =
                new AuthenticationFailedException(errorMessage);
        ExceptionDto authenticationFailedDto = mapper.toExceptionDto(authenticationFailedException);
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getCode(), authenticationFailedDto.getCode());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), authenticationFailedDto.getHttpStatusCode());

        // AuthorizationFailedException
        AuthorizationFailedException authorizationFailedException =
                new AuthorizationFailedException(errorMessage);
        ExceptionDto authorizationFailedDto = mapper.toExceptionDto(authorizationFailedException);
        assertEquals(ExceptionCode.CLIENT_NOT_AUTHORIZED.getCode(), authorizationFailedDto.getCode());
        assertEquals(HttpStatus.FORBIDDEN.value(), authorizationFailedDto.getHttpStatusCode());

        // ParameterValidationFailedException
        ParameterValidationFailedException parameterValidationFailedException =
                new ParameterValidationFailedException(errorMessage);
        ExceptionDto parameterValidationFailedDto = mapper.toExceptionDto(parameterValidationFailedException);
        assertEquals(ExceptionCode.PARAMETER_VALIDATION_ERROR.getCode(), parameterValidationFailedDto.getCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), parameterValidationFailedDto.getHttpStatusCode());

        // InternalServerErrorException
        InternalServerErrorException internalServerErrorException =
                new InternalServerErrorException(errorMessage);
        ExceptionDto internalServerErrorDto = mapper.toExceptionDto(internalServerErrorException);
        assertEquals(ExceptionCode.INTERNAL_SERVER_ERROR.getCode(), internalServerErrorDto.getCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), internalServerErrorDto.getHttpStatusCode());

        // Generic BusinessException
        for (ExceptionCode exceptionCode : ExceptionCode.values()) {
            BusinessException businessException = new BusinessException(exceptionCode, errorMessage);
            ExceptionDto businessExceptionDto = mapper.toExceptionDto(businessException);
            assertEquals(exceptionCode.getCode(), businessExceptionDto.getCode());
            assertEquals(exceptionCode.getHttpStatusCode(), businessExceptionDto.getHttpStatusCode());
            assertEquals(exceptionCode.getReason(), businessExceptionDto.getReason());
            assertEquals(errorMessage, businessExceptionDto.getMessage());
        }
    }

    @Test
    void shouldCorrectlyMapExceptionWithNullMessage() {
        for (ExceptionCode exceptionCode : ExceptionCode.values()) {
            BusinessException exception = new BusinessException(exceptionCode);
            ExceptionDto dto = mapper.toExceptionDto(exception);

            assertNotNull(dto);
            assertEquals(exceptionCode.getCode(), dto.getCode());
            assertEquals(exceptionCode.getHttpStatusCode(), dto.getHttpStatusCode());
            assertEquals(exceptionCode.getReason(), dto.getReason());
            assertNull(dto.getMessage());
        }
    }

    @Test
    void shouldVerifyAllExceptionCodesAreMapped() {
        int expectedExceptionCodeCount = ExceptionCode.values().length;
        int mappedExceptionCount = 0;

        for (ExceptionCode exceptionCode : ExceptionCode.values()) {
            BusinessException exception = new BusinessException(exceptionCode);
            ExceptionDto dto = mapper.toExceptionDto(exception);

            if (dto != null && dto.getCode().equals(exceptionCode.getCode())) {
                mappedExceptionCount++;
            }
        }

        assertEquals(expectedExceptionCodeCount, mappedExceptionCount,
                "All exception codes should be properly mapped");
    }

    @Test
    void shouldReturnNullWhenMappingNull() {
        assertNull(mapper.toExceptionDto(null));
    }
}
