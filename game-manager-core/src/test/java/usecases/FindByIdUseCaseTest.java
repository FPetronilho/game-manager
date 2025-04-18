package usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import com.tracktainment.gamemanager.usecases.FindByIdUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByIdUseCaseTest {

    @Mock
    private GameDataProvider gameDataProvider;

    @Mock
    private DuxManagerDataProvider duxManagerDataProvider;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private FindByIdUseCase findByIdUseCase;

    private Game game;
    private DigitalUser digitalUser;
    private AssetResponse assetResponse;
    private String jwt;
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID().toString();
        game = TestGameDataUtil.createTestGame();
        digitalUser = TestGameDataUtil.createTestDigitalUser();
        assetResponse = TestGameDataUtil.createTestAssetResponse();
        jwt = "Bearer token";
    }

    @Test
    void shouldFindGameByIdSuccessfully() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(Collections.singletonList(assetResponse));
        when(gameDataProvider.findById(gameId)).thenReturn(game);

        FindByIdUseCase.Input input = FindByIdUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act
        FindByIdUseCase.Output output = findByIdUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(game, output.getGame());

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider).findById(gameId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenAssetNotFound() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(Collections.emptyList());

        FindByIdUseCase.Input input = FindByIdUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> findByIdUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider, never()).findById(any());
    }

    @Test
    void shouldPropagateGameDataProviderExceptions() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(Collections.singletonList(assetResponse));
        when(gameDataProvider.findById(gameId)).thenThrow(new ResourceNotFoundException(Game.class, gameId));

        FindByIdUseCase.Input input = FindByIdUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> findByIdUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(gameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider).findById(gameId);
    }
}
