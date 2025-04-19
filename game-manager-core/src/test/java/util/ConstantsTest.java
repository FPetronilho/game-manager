package util;

import com.tracktainment.gamemanager.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void shouldThrowExceptionWhenInstantiated() throws NoSuchMethodException {
        // Arrange
        Constructor<Constants> constructor = Constants.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Act & Assert
        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        Throwable cause = exception.getCause();
        assertEquals(IllegalStateException.class, cause.getClass());
        assertEquals("Cannot instantiate an util class.", cause.getMessage());
    }

    @Test
    void shouldHaveCorrectDefaultValues() {
        // Assert
        assertEquals("0", Constants.DEFAULT_OFFSET);
        assertEquals("10", Constants.DEFAULT_LIMIT);
        assertEquals(0, Constants.MIN_OFFSET);
        assertEquals(1, Constants.MIN_LIMIT);
        assertEquals(100, Constants.MAX_LIMIT);
        assertEquals("TITLE", Constants.DEFAULT_ORDER);
        assertEquals("ASC", Constants.DEFAULT_DIRECTION);
    }

    @ParameterizedTest
    @MethodSource("provideValidRegexMatches")
    void shouldMatchValidRegexPatterns(String input, String pattern) {
        // Assert
        assertTrue(input.matches(pattern), "Input '" + input + "' should match pattern '" + pattern + "'");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRegexMatches")
    void shouldNotMatchInvalidRegexPatterns(String input, String pattern) {
        // Assert
        assertFalse(input.matches(pattern), "Input '" + input + "' should not match pattern '" + pattern + "'");
    }

    @Test
    void shouldHaveCorrectErrorMessages() {
        // Assert for mandatory field messages
        assertTrue(Constants.TITLE_MANDATORY_MSG.contains("mandatory"));
        assertTrue(Constants.PLATFORM_MANDATORY_MSG.contains("mandatory"));

        // Assert for validation error messages
        assertTrue(Constants.ID_INVALID_MSG.contains("must match"));
        assertTrue(Constants.TITLE_INVALID_MSG.contains("must match"));
        assertTrue(Constants.OFFSET_INVALID_MSG.contains("must be positive"));
        assertTrue(Constants.LIMIT_INVALID_MSG.contains("range"));
    }

    @Test
    void shouldHaveCorrectRegexPatterns() {
        // ID regex should match UUID format
        assertNotNull(Constants.ID_REGEX);
        assertTrue(UUID.randomUUID().toString().matches(Constants.ID_REGEX));

        // Other regexes should be defined
        assertNotNull(Constants.TITLE_REGEX);
        assertNotNull(Constants.PLATFORM_REGEX);
        assertNotNull(Constants.GENRE_REGEX);
        assertNotNull(Constants.DEVELOPER_REGEX);
    }

    private static Stream<Arguments> provideValidRegexMatches() {
        return Stream.of(
                // ID_REGEX
                Arguments.of(UUID.randomUUID().toString(), Constants.ID_REGEX),

                // TITLE_REGEX
                Arguments.of("The Last of Us Part II", Constants.TITLE_REGEX),
                Arguments.of("God of War", Constants.TITLE_REGEX),
                Arguments.of("Cyberpunk 2077", Constants.TITLE_REGEX),

                // PLATFORM_REGEX
                Arguments.of("PS5", Constants.PLATFORM_REGEX),
                Arguments.of("Switch", Constants.PLATFORM_REGEX),
                Arguments.of("PC", Constants.PLATFORM_REGEX),

                // GENRE_REGEX
                Arguments.of("Action", Constants.GENRE_REGEX),
                Arguments.of("Role-Playing", Constants.GENRE_REGEX),
                Arguments.of("Simulation", Constants.GENRE_REGEX),

                // DEVELOPER_REGEX
                Arguments.of("Naughty Dog", Constants.DEVELOPER_REGEX),
                Arguments.of("CD Projekt Red", Constants.DEVELOPER_REGEX),
                Arguments.of("Bethesda Game Studios", Constants.DEVELOPER_REGEX),

                // ID_LIST_REGEX
                Arguments.of(UUID.randomUUID().toString(), Constants.ID_LIST_REGEX),
                Arguments.of(UUID.randomUUID().toString() + "," + UUID.randomUUID().toString(), Constants.ID_LIST_REGEX)
        );
    }

    private static Stream<Arguments> provideInvalidRegexMatches() {
        return Stream.of(
                // ID_REGEX - not a UUID
                Arguments.of("not-a-uuid", Constants.ID_REGEX),

                // TITLE_REGEX - too long
                Arguments.of("a".repeat(201), Constants.TITLE_REGEX),

                // PLATFORM_REGEX - invalid characters
                Arguments.of("Platform+", Constants.PLATFORM_REGEX),

                // GENRE_REGEX - invalid characters
                Arguments.of("Genre123", Constants.GENRE_REGEX),

                // DEVELOPER_REGEX - too long
                Arguments.of("a".repeat(151), Constants.DEVELOPER_REGEX),

                // ID_LIST_REGEX - invalid format
                Arguments.of("not-a-uuid-list", Constants.ID_LIST_REGEX),
                Arguments.of("invalid,uuid,list", Constants.ID_LIST_REGEX)
        );
    }
}
