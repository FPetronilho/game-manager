package usecases;

import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.usecases.FindByIdUseCase;
import com.tracktainment.gamemanager.usecases.UpdateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUseCaseTest {

    @Mock
    private GameDataProvider gameDataProvider;

    @Mock
    private FindByIdUseCase findByIdUseCase;

    @InjectMocks
    private UpdateUseCase updateUseCase;

    private Game game;
    private Game updatedGame;
    private GameUpdate gameUpdate;
    private String jwt;
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID().toString();
        game = TestGameDataUtil.createTestGame();
        gameUpdate = TestGameDataUtil.createTestGameUpdate();
        updatedGame = TestGameDataUtil.createTestGameWithUpdate();
        jwt = "Bearer token";
    }

    @Test
    void shouldUpdateGameSuccessfully() {
        // Arrange
        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class))).thenReturn(
                FindByIdUseCase.Output.builder().game(game).build()
        );

        when(gameDataProvider.update(eq(gameId), eq(gameUpdate))).thenReturn(updatedGame);

        UpdateUseCase.Input input = UpdateUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .gameUpdate(gameUpdate)
                .build();

        // Act
        UpdateUseCase.Output output = updateUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(updatedGame, output.getGame());

        verify(findByIdUseCase).execute(any(FindByIdUseCase.Input.class));
        verify(gameDataProvider).update(gameId, gameUpdate);
    }

    @Test
    void shouldPropagateExceptionWhenGameNotFound() {
        // Arrange
        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class)))
                .thenThrow(new ResourceNotFoundException(Game.class, gameId));

        UpdateUseCase.Input input = UpdateUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .gameUpdate(gameUpdate)
                .build();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> updateUseCase.execute(input));

        verify(findByIdUseCase).execute(any(FindByIdUseCase.Input.class));
        verify(gameDataProvider, never()).update(any(), any());
    }

    @Test
    void shouldPropagateExceptionFromGameDataProvider() {
        // Arrange
        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class))).thenReturn(
                FindByIdUseCase.Output.builder().game(game).build()
        );

        when(gameDataProvider.update(eq(gameId), eq(gameUpdate)))
                .thenThrow(new RuntimeException("Database error"));

        UpdateUseCase.Input input = UpdateUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .gameUpdate(gameUpdate)
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> updateUseCase.execute(input));

        verify(findByIdUseCase).execute(any(FindByIdUseCase.Input.class));
        verify(gameDataProvider).update(gameId, gameUpdate);
    }

    @Test
    void shouldPassTheCorrectInputToFindByIdUseCase() {
        // Arrange
        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class))).thenReturn(
                FindByIdUseCase.Output.builder().game(game).build()
        );

        when(gameDataProvider.update(eq(gameId), eq(gameUpdate))).thenReturn(updatedGame);

        UpdateUseCase.Input input = UpdateUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .gameUpdate(gameUpdate)
                .build();

        // Act
        updateUseCase.execute(input);

        // Assert
        verify(findByIdUseCase).execute(argThat(findByIdInput ->
                findByIdInput.getJwt().equals(jwt) &&
                        findByIdInput.getId().equals(gameId)
        ));
    }
}
