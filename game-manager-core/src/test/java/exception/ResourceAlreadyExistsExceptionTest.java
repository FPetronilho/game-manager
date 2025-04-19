package exception;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.exception.ExceptionCode;
import com.tracktainment.gamemanager.exception.ResourceAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ResourceAlreadyExistsExceptionTest {

    @Test
    void shouldCreateResourceAlreadyExistsExceptionWithClassAndAttribute() {
        // Arrange
        Class<?> resourceClass = Game.class;
        String resourceAttribute = "The Last of Us Part II";

        // Act
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(resourceClass, resourceAttribute);

        // Assert
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getCode(), exception.getCode());
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getHttpStatusCode(), exception.getHttpStatusCode());
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getReason(), exception.getReason());
        assertEquals(String.format("Game %s already exists.", resourceAttribute), exception.getMessage());
    }

    @Test
    void shouldBeSubclassOfBusinessException() {
        // Arrange
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(Game.class, "test");

        // Assert
        assertTrue(exception instanceof com.tracktainment.gamemanager.exception.BusinessException);
    }

    @Test
    void shouldHaveCorrectExceptionCode() {
        // Arrange
        ResourceAlreadyExistsException exception = new ResourceAlreadyExistsException(Game.class, "test");

        // Assert
        assertEquals(ExceptionCode.RESOURCE_ALREADY_EXISTS.getCode(), exception.getCode());
        assertEquals(409, exception.getHttpStatusCode());
        assertEquals("Resource already exists.", exception.getReason());
    }

    @Test
    void shouldFormatMessageWithDifferentResourceTypes() {
        // Arrange
        String gameId = UUID.randomUUID().toString();

        // Act
        ResourceAlreadyExistsException gameException = new ResourceAlreadyExistsException(Game.class, gameId);
        ResourceAlreadyExistsException stringException = new ResourceAlreadyExistsException(String.class, "test");

        // Assert
        assertEquals(String.format("Game %s already exists.", gameId), gameException.getMessage());
        assertEquals("String test already exists.", stringException.getMessage());
    }
}
