package usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import com.tracktainment.gamemanager.usecases.DeleteUseCase;
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
class DeleteUseCaseTest {

    @Mock
    private GameDataProvider gameDataProvider;

    @Mock
    private DuxManagerDataProvider duxManagerDataProvider;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private DeleteUseCase deleteUseCase;

    private DigitalUser digitalUser;
    private String jwt;
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID().toString();
        digitalUser = TestGameDataUtil.createTestDigitalUser();
        jwt = "Bearer token";
    }

    @Test
    void shouldDeleteGameSuccessfully() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        doNothing().when(duxManagerDataProvider).deleteAsset(jwt, digitalUser.getId(), gameId);
        doNothing().when(gameDataProvider).delete(gameId);

        DeleteUseCase.Input input = DeleteUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertDoesNotThrow(() -> deleteUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).deleteAsset(jwt, digitalUser.getId(), gameId);
        verify(gameDataProvider).delete(gameId);
    }

    @Test
    void shouldPropagateExceptionFromDuxManagerDataProvider() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        doThrow(new RuntimeException("Failed to delete asset")).when(duxManagerDataProvider)
                .deleteAsset(jwt, digitalUser.getId(), gameId);

        DeleteUseCase.Input input = DeleteUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deleteUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).deleteAsset(jwt, digitalUser.getId(), gameId);
        verify(gameDataProvider, never()).delete(any());
    }

    @Test
    void shouldPropagateExceptionFromGameDataProvider() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        doNothing().when(duxManagerDataProvider).deleteAsset(jwt, digitalUser.getId(), gameId);
        doThrow(new ResourceNotFoundException(Game.class, gameId)).when(gameDataProvider).delete(gameId);

        DeleteUseCase.Input input = DeleteUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> deleteUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).deleteAsset(jwt, digitalUser.getId(), gameId);
        verify(gameDataProvider).delete(gameId);
    }

    @Test
    void shouldPropagateExceptionFromSecurityUtil() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenThrow(new IllegalStateException("JWT not found in security context"));

        DeleteUseCase.Input input = DeleteUseCase.Input.builder()
                .jwt(jwt)
                .id(gameId)
                .build();

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> deleteUseCase.execute(input));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider, never()).deleteAsset(any(), any(), any());
        verify(gameDataProvider, never()).delete(any());
    }
}
