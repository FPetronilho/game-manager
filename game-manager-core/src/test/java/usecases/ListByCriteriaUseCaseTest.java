package usecases;

import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProvider;
import com.tracktainment.gamemanager.dataprovider.GameDataProvider;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.security.context.DigitalUser;
import com.tracktainment.gamemanager.security.util.SecurityUtil;
import com.tracktainment.gamemanager.usecases.ListByCriteriaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListByCriteriaUseCaseTest {

    @Mock
    private GameDataProvider gameDataProvider;

    @Mock
    private DuxManagerDataProvider duxManagerDataProvider;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private ListByCriteriaUseCase listByCriteriaUseCase;

    private Game game1;
    private Game game2;
    private DigitalUser digitalUser;
    private AssetResponse assetResponse1;
    private AssetResponse assetResponse2;
    private String jwt;
    private List<Game> games;
    private List<AssetResponse> assetResponses;

    @BeforeEach
    void setUp() {
        game1 = TestGameDataUtil.createTestGame();
        game2 = Game.builder()
                .id(UUID.randomUUID().toString())
                .title("God of War: Ragnarok")
                .platform("PlayStation 5")
                .genre("Action Adventure")
                .developer("Santa Monica Studio")
                .releaseDate(LocalDate.of(2022, 11, 9))
                .build();

        digitalUser = TestGameDataUtil.createTestDigitalUser();

        assetResponse1 = AssetResponse.builder()
                .externalId(game1.getId())
                .type("game")
                .build();

        assetResponse2 = AssetResponse.builder()
                .externalId(game2.getId())
                .type("game")
                .build();

        jwt = "Bearer token";

        games = Arrays.asList(game1, game2);
        assetResponses = Arrays.asList(assetResponse1, assetResponse2);
    }

    @Test
    void shouldListGamesByCriteriaSuccessfully() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                isNull(),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull())
        ).thenReturn(assetResponses);

        when(gameDataProvider.listByCriteria(any(ListByCriteriaUseCase.Input.class))).thenReturn(games);

        List<OrderBy> orderByList = Collections.singletonList(OrderBy.TITLE);
        List<OrderDirection> orderDirectionList = Collections.singletonList(OrderDirection.ASC);

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .jwt(jwt)
                .offset(0)
                .limit(10)
                .orderByList(orderByList)
                .orderDirectionList(orderDirectionList)
                .build();

        // Act
        ListByCriteriaUseCase.Output output = listByCriteriaUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(games, output.getGames());
        assertEquals(2, output.getGames().size());

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                isNull(),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider).listByCriteria(any(ListByCriteriaUseCase.Input.class));
    }

    @Test
    void shouldHandleEmptyAssetsList() {
        // Arrange
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                any(), any(), any(), any(), any(), any(), any(), any(), any())
        ).thenReturn(Collections.emptyList());

        when(gameDataProvider.listByCriteria(any(ListByCriteriaUseCase.Input.class))).thenReturn(Collections.emptyList());

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .jwt(jwt)
                .offset(0)
                .limit(10)
                .build();

        // Act
        ListByCriteriaUseCase.Output output = listByCriteriaUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertNotNull(output.getGames());
        assertTrue(output.getGames().isEmpty());

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                isNull(),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider).listByCriteria(any(ListByCriteriaUseCase.Input.class));
    }

    @Test
    void shouldFilterBySpecificId() {
        // Arrange
        String specificGameId = game1.getId();
        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(specificGameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull())
        ).thenReturn(Collections.singletonList(assetResponse1));

        when(gameDataProvider.listByCriteria(any(ListByCriteriaUseCase.Input.class))).thenReturn(Collections.singletonList(game1));

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .jwt(jwt)
                .offset(0)
                .limit(10)
                .ids(specificGameId)
                .build();

        // Act
        ListByCriteriaUseCase.Output output = listByCriteriaUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(1, output.getGames().size());
        assertEquals(game1, output.getGames().get(0));

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                eq(specificGameId),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                isNull(),
                isNull()
        );
        verify(gameDataProvider).listByCriteria(any(ListByCriteriaUseCase.Input.class));
    }

    @Test
    void shouldFilterByDateRange() {
        // Arrange
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        when(securityUtil.getDigitalUser()).thenReturn(digitalUser);
        when(duxManagerDataProvider.findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                isNull(),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                eq(from),
                eq(to))
        ).thenReturn(assetResponses);

        when(gameDataProvider.listByCriteria(any(ListByCriteriaUseCase.Input.class))).thenReturn(games);

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .jwt(jwt)
                .offset(0)
                .limit(10)
                .from(from)
                .to(to)
                .build();

        // Act
        ListByCriteriaUseCase.Output output = listByCriteriaUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(2, output.getGames().size());

        verify(securityUtil).getDigitalUser();
        verify(duxManagerDataProvider).findAssetsByCriteria(
                eq(jwt),
                eq(digitalUser.getId()),
                isNull(),
                eq("com.tracktainment"),
                eq("game-manager"),
                eq("game"),
                isNull(),
                eq(from),
                eq(to)
        );
        verify(gameDataProvider).listByCriteria(any(ListByCriteriaUseCase.Input.class));
    }
}
