package usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.exception.ResourceAlreadyExistsException;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import com.tracktainment.gamemanager.usecases.CreateUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUseCaseTest {

    @Mock
    private GameDataProvider gameDataProvider;

    @Mock
    private DuxManagerDataProvider duxManagerDataProvider;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CreateUseCase createUseCase;

    private GameCreate gameCreate;
    private Game game;
    private DigitalUser digitalUser;
    private AssetResponse assetResponse;
    private String jwt;

    @BeforeEach
    void setUp() {
        gameCreate = TestGameDataUtil.createTestGameCreate();
        game = TestGameDataUtil.createTestGame();
        digitalUser = TestGameDataUtil.createTestDigitalUser();
        assetResponse = TestGameDataUtil.createTestAssetResponse();
        jwt = "Bearer token";
    }

    @Test
    void shouldCreateGameSuccessfully() {
        // Arrange
        when(gameDataProvider.create(any(GameCreate.class)))
                .thenReturn(game);
        when(securityUtil.getDigitalUser())
                .thenReturn(digitalUser);
        when(duxManagerDataProvider.createAsset(eq(jwt), eq(digitalUser.getId()), any(AssetRequest.class)))
                .thenReturn(assetResponse);

        CreateUseCase.Input input = CreateUseCase.Input.builder()
                .jwt(jwt)
                .gameCreate(gameCreate)
                .build();

        // Act
        CreateUseCase.Output output = createUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(game, output.getGame());

        verify(gameDataProvider).create(gameCreate);
        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).createAsset(eq(jwt), eq(digitalUser.getId()), any(AssetRequest.class));
    }

    @Test
    void shouldRollbackGameCreationWhenAssetCreationFails() {
        // Arrange
        when(gameDataProvider.create(any(GameCreate.class)))
                .thenReturn(game);
        when(securityUtil.getDigitalUser())
                .thenReturn(digitalUser);
        when(duxManagerDataProvider.createAsset(eq(jwt), eq(digitalUser.getId()), any(AssetRequest.class)))
                .thenThrow(new RuntimeException("Failed to create asset"));

        CreateUseCase.Input input = CreateUseCase.Input.builder()
                .jwt(jwt)
                .gameCreate(gameCreate)
                .build();

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> createUseCase.execute(input));
        assertEquals("Failed to create asset", exception.getMessage());

        verify(gameDataProvider).create(gameCreate);
        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).createAsset(eq(jwt), eq(digitalUser.getId()), any(AssetRequest.class));
        verify(gameDataProvider).delete(game.getId());
    }

    @Test
    void shouldPropagateGameDataProviderExceptions() {
        // Arrange
        when(gameDataProvider.create(any(GameCreate.class)))
                .thenThrow(new ResourceAlreadyExistsException(Game.class, gameCreate.getTitle()));

        CreateUseCase.Input input = CreateUseCase.Input.builder()
                .jwt(jwt)
                .gameCreate(gameCreate)
                .build();

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> createUseCase.execute(input));

        verify(gameDataProvider).create(gameCreate);
        verify(securityUtil, never()).getDigitalUser();
        verify(duxManagerDataProvider, never()).createAsset(any(), any(), any());
        verify(gameDataProvider, never()).delete(any());
    }
}
