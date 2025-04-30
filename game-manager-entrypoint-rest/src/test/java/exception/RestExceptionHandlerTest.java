package exception;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.exception.*;
import com.tracktainment.gamemanager.mapper.ExceptionMapperEntryPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {

    @Mock
    private ExceptionMapperEntryPoint mapper;

    @InjectMocks
    private RestExceptionHandler handler;

    private ExceptionDto exceptionDto;

    @BeforeEach
    void setUp() {
        exceptionDto = new ExceptionDto();
    }

    @Test
    void shouldHandleBusinessException() {
        // Arrange
        String errorMessage = "Resource not found";
        BusinessException exception = new BusinessException(ExceptionCode.RESOURCE_NOT_FOUND, errorMessage);

        exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.RESOURCE_NOT_FOUND.getCode())
                .httpStatusCode(ExceptionCode.RESOURCE_NOT_FOUND.getHttpStatusCode())
                .reason(ExceptionCode.RESOURCE_NOT_FOUND.getReason())
                .message(errorMessage)
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleResourceNotFoundException() {
        // Arrange
        String resourceId = UUID.randomUUID().toString();
        ResourceNotFoundException exception = new ResourceNotFoundException(Game.class, resourceId);

        exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.RESOURCE_NOT_FOUND.getCode())
                .httpStatusCode(ExceptionCode.RESOURCE_NOT_FOUND.getHttpStatusCode())
                .reason(ExceptionCode.RESOURCE_NOT_FOUND.getReason())
                .message(String.format("Game %s not found.", resourceId))
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleResourceAlreadyExistsException() {
        // Arrange
        String resourceId = UUID.randomUUID().toString();
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(Game.class, resourceId);

        exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.RESOURCE_ALREADY_EXISTS.getCode())
                .httpStatusCode(ExceptionCode.RESOURCE_ALREADY_EXISTS.getHttpStatusCode())
                .reason(ExceptionCode.RESOURCE_ALREADY_EXISTS.getReason())
                .message(String.format("Game %s already exists.", resourceId))
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleAuthenticationFailedException() {
        // Arrange
        String errorMessage = "Authentication failed";
        AuthenticationFailedException exception = new AuthenticationFailedException(errorMessage);

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getCode())
                .httpStatusCode(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getHttpStatusCode())
                .reason(ExceptionCode.CLIENT_NOT_AUTHENTICATED.getReason())
                .message(errorMessage)
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleAuthorizationFailedException() {
        // Arrange
        String errorMessage = "Authorization failed";
        AuthorizationFailedException exception = new AuthorizationFailedException(errorMessage);

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.CLIENT_NOT_AUTHORIZED.getCode())
                .httpStatusCode(ExceptionCode.CLIENT_NOT_AUTHORIZED.getHttpStatusCode())
                .reason(ExceptionCode.CLIENT_NOT_AUTHORIZED.getReason())
                .message(errorMessage)
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleParameterValidationFailedException() {
        // Arrange
        String errorMessage = "Invalid parameter values";
        ParameterValidationFailedException exception = new ParameterValidationFailedException(errorMessage);

        exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.PARAMETER_VALIDATION_ERROR.getCode())
                .httpStatusCode(ExceptionCode.PARAMETER_VALIDATION_ERROR.getHttpStatusCode())
                .reason(ExceptionCode.PARAMETER_VALIDATION_ERROR.getReason())
                .message(errorMessage)
                .build();

        when(mapper.toExceptionDto(exception))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(exception);
    }

    @Test
    void shouldHandleGlobalException() {
        // Arrange
        String errorMessage = "Unexpected error";
        RuntimeException runtimeException = new RuntimeException(errorMessage);

        exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.INTERNAL_SERVER_ERROR.getCode())
                .httpStatusCode(ExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatusCode())
                .reason(ExceptionCode.INTERNAL_SERVER_ERROR.getReason())
                .message(errorMessage)
                .build();

        when(mapper.toExceptionDto(any(InternalServerErrorException.class)))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleGlobalException(runtimeException);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());

        verify(mapper).toExceptionDto(any(InternalServerErrorException.class));
    }

    @Test
    void shouldHandleEachExceptionCodeType() {
        for (ExceptionCode exceptionCode : ExceptionCode.values()) {
            // Arrange
            String errorMessage = "Test error for " + exceptionCode.name();
            BusinessException exception = new BusinessException(exceptionCode, errorMessage);

            ExceptionDto expectedDto = ExceptionDto.builder()
                    .code(exceptionCode.getCode())
                    .httpStatusCode(exceptionCode.getHttpStatusCode())
                    .reason(exceptionCode.getReason())
                    .message(errorMessage)
                    .build();

            when(mapper.toExceptionDto(exception))
                    .thenReturn(expectedDto);

            // Act
            ResponseEntity<ExceptionDto> response = handler.handleBusinessException(exception);

            // Assert
            assertNotNull(response);
            assertEquals(exceptionCode.getHttpStatusCode(), response.getStatusCodeValue());
            assertEquals(expectedDto, response.getBody());
            assertEquals(errorMessage, response.getBody().getMessage());

            // Verify
            verify(mapper).toExceptionDto(exception);
        }
    }

    @Test
    void shouldHandleVariousRuntimeExceptions() {
        // Arrange
        RuntimeException[] exceptions = {
                new NullPointerException("Null reference"),
                new IllegalArgumentException("Invalid argument"),
                new UnsupportedOperationException("Operation not supported"),
                new IllegalStateException("Invalid state")
        };

        for (RuntimeException exception : exceptions) {
            ExceptionDto exceptionDto = ExceptionDto.builder()
                    .code(ExceptionCode.INTERNAL_SERVER_ERROR.getCode())
                    .httpStatusCode(ExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatusCode())
                    .reason(ExceptionCode.INTERNAL_SERVER_ERROR.getReason())
                    .message(exception.getMessage())
                    .build();

            when(mapper.toExceptionDto(any(InternalServerErrorException.class)))
                    .thenReturn(exceptionDto);

            // Act
            ResponseEntity<ExceptionDto> response = handler.handleGlobalException(exception);

            // Assert
            assertNotNull(response);
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
            assertEquals(exceptionDto, response.getBody());
            assertEquals(exception.getMessage(), response.getBody().getMessage());
        }
    }

    @Test
    void shouldHandleCheckedExceptions() {
        // Arrange
        Exception exception = new Exception("Checked exception");

        ExceptionDto exceptionDto = ExceptionDto.builder()
                .code(ExceptionCode.INTERNAL_SERVER_ERROR.getCode())
                .httpStatusCode(ExceptionCode.INTERNAL_SERVER_ERROR.getHttpStatusCode())
                .reason(ExceptionCode.INTERNAL_SERVER_ERROR.getReason())
                .message(exception.getMessage())
                .build();

        when(mapper.toExceptionDto(any(InternalServerErrorException.class)))
                .thenReturn(exceptionDto);

        // Act
        ResponseEntity<ExceptionDto> response = handler.handleGlobalException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatusCodeValue());
        assertEquals(exceptionDto, response.getBody());
        assertEquals(exception.getMessage(), response.getBody().getMessage());
    }
}
