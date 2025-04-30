package controller;

import com.tracktainment.gamemanager.controller.GameController;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.exception.ParameterValidationFailedException;
import com.tracktainment.gamemanager.usecases.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import testutil.TestGameDataUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private CreateUseCase createUseCase;

    @Mock
    private FindByIdUseCase findByIdUseCase;

    @Mock
    private ListByCriteriaUseCase listByCriteriaUseCase;

    @Mock
    private UpdateUseCase updateUseCase;

    @Mock
    private DeleteUseCase deleteUseCase;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private GameController gameController;

    private GameCreate gameCreate;
    private GameUpdate gameUpdate;
    private Game game;
    private String jwt;
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID().toString();
        gameCreate = TestGameDataUtil.createTestGameCreate();
        gameUpdate = TestGameDataUtil.createTestGameUpdate();
        game = TestGameDataUtil.createTestGame();
        jwt = "Bearer token";

        lenient().when(httpServletRequest.getHeader("Authorization")).thenReturn(jwt);
    }

    @Test
    void shouldCreateGameSuccessfully() {
        // Arrange
        CreateUseCase.Output output = CreateUseCase.Output.builder()
                .game(game)
                .build();

        when(createUseCase.execute(any(CreateUseCase.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<Game> response = gameController.create(gameCreate);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(game, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(createUseCase).execute(any(CreateUseCase.Input.class));
    }

    @Test
    void shouldFindGameByIdSuccessfully() {
        // Arrange
        FindByIdUseCase.Output output = FindByIdUseCase.Output.builder()
                .game(game)
                .build();

        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<Game> response = gameController.findById(gameId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(game, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(findByIdUseCase).execute(any(FindByIdUseCase.Input.class));
    }

    @Test
    void shouldListGamesByCriteriaSuccessfully() {
        // Arrange
        List<Game> games = Arrays.asList(game, TestGameDataUtil.createTestGameWithUpdate());
        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(games)
                .build();

        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, null, null, null, null, null,
                null, null, null, null,
                Collections.singletonList(OrderBy.TITLE),
                Collections.singletonList(OrderDirection.ASC)
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(games, response.getBody());
        assertEquals(2, response.getBody().size());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(any(ListByCriteriaUseCase.Input.class));
    }

    @Test
    void shouldListGamesByCriteriaWithCreatedAtParameter() {
        // Arrange
        List<Game> games = Collections.singletonList(game);
        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(games)
                .build();

        LocalDate createdAt = LocalDate.now();
        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, null, null, null, null, null,
                null, createdAt, LocalDate.now().minusDays(7), LocalDate.now(),
                Collections.singletonList(OrderBy.TITLE),
                Collections.singletonList(OrderDirection.ASC)
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(games, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(argThat(input ->
                input.getCreatedAt() == createdAt &&
                        input.getFrom() == null &&
                        input.getTo() == null
        ));
    }

    @Test
    void shouldListGamesByCriteriaWithAllFilterParameters() {
        // Arrange
        List<Game> games = Collections.singletonList(game);
        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(games)
                .build();

        String title = "Last of Us";
        String platform = "PlayStation";
        String genre = "Action";
        String developer = "Naughty Dog";
        LocalDate releaseDate = LocalDate.of(2020, 6, 19);
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, null, title, platform, genre, developer,
                releaseDate, null, from, to,
                Collections.singletonList(OrderBy.TITLE),
                Collections.singletonList(OrderDirection.ASC)
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(games, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(argThat(input ->
                input.getTitle().equals(title) &&
                        input.getPlatform().equals(platform) &&
                        input.getGenre().equals(genre) &&
                        input.getDeveloper().equals(developer) &&
                        input.getReleaseDate().equals(releaseDate) &&
                        input.getFrom().equals(from) &&
                        input.getTo().equals(to)
        ));
    }

    @Test
    void shouldListGamesByCriteriaWithIdsParameter() {
        // Arrange
        List<Game> games = Collections.singletonList(game);
        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(games)
                .build();

        String ids = UUID.randomUUID().toString() + "," + UUID.randomUUID().toString();
        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, ids, null, null, null, null,
                null, null, null, null,
                Collections.singletonList(OrderBy.TITLE),
                Collections.singletonList(OrderDirection.ASC)
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(games, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(argThat(input ->
                input.getIds().equals(ids)
        ));
    }

    @Test
    void shouldListGamesByCriteriaWithMultipleOrderingCriteria() {
        // Arrange
        List<Game> games = Collections.singletonList(game);
        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(games)
                .build();

        List<OrderBy> orderByList = Arrays.asList(OrderBy.TITLE, OrderBy.GENRE);
        List<OrderDirection> orderDirectionList = Arrays.asList(OrderDirection.ASC, OrderDirection.DESC);

        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, null, null, null, null, null,
                null, null, null, null,
                orderByList,
                orderDirectionList
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(games, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(argThat(input ->
                input.getOrderByList().equals(orderByList) &&
                        input.getOrderDirectionList().equals(orderDirectionList)
        ));
    }

    @Test
    void shouldThrowExceptionWhenOrderByAndOrderDirectionSizesDontMatch() {
        // Arrange
        List<OrderBy> orderByList = Arrays.asList(OrderBy.TITLE, OrderBy.PLATFORM);
        List<OrderDirection> orderDirectionList = Collections.singletonList(OrderDirection.ASC);

        // Act & Assert
        assertThrows(ParameterValidationFailedException.class, () ->
                gameController.listByCriteria(
                        0, 10, null, null, null, null, null,
                        null, null, null, null,
                        orderByList, orderDirectionList
                )
        );

        verify(listByCriteriaUseCase, never()).execute(any());
    }

    @Test
    void shouldThrowExceptionWhenToDateIsBeforeFromDate() {
        // Arrange
        LocalDate from = LocalDate.now();
        LocalDate to = from.minusDays(1);

        // Act & Assert
        assertThrows(ParameterValidationFailedException.class, () ->
                gameController.listByCriteria(
                        0, 10, null, null, null, null, null,
                        null, null, from, to,
                        Collections.singletonList(OrderBy.TITLE),
                        Collections.singletonList(OrderDirection.ASC)
                )
        );

        verify(listByCriteriaUseCase, never()).execute(any());
    }

    @Test
    void shouldIgnoreFromAndToWhenCreatedAtIsProvided() {
        // Arrange
        LocalDate createdAt = LocalDate.now();
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        ListByCriteriaUseCase.Output output = ListByCriteriaUseCase.Output.builder()
                .games(Collections.singletonList(game))
                .build();

        when(listByCriteriaUseCase.execute(any(ListByCriteriaUseCase.Input.class)))
                .thenReturn(output);

        // Act
        ResponseEntity<List<Game>> response = gameController.listByCriteria(
                0, 10, null, null, null, null, null,
                null, createdAt, from, to,
                Collections.singletonList(OrderBy.TITLE),
                Collections.singletonList(OrderDirection.ASC)
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(httpServletRequest).getHeader("Authorization");
        verify(listByCriteriaUseCase).execute(argThat(input ->
                input.getCreatedAt() == createdAt &&
                        input.getFrom() == null &&
                        input.getTo() == null
        ));
    }

    @Test
    void shouldUpdateGameSuccessfully() {
        // Arrange
        Game updatedGame = TestGameDataUtil.createTestGameWithUpdate();
        UpdateUseCase.Output output = UpdateUseCase.Output.builder()
                .game(updatedGame)
                .build();

        when(updateUseCase.execute(any(UpdateUseCase.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<Game> response = gameController.update(gameId, gameUpdate);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedGame, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(updateUseCase).execute(any(UpdateUseCase.Input.class));
    }

    @Test
    void shouldDeleteGameSuccessfully() {
        // Arrange
        doNothing().when(deleteUseCase).execute(any(DeleteUseCase.Input.class));

        // Act
        ResponseEntity<Void> response = gameController.delete(gameId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(deleteUseCase).execute(any(DeleteUseCase.Input.class));
    }

    @Test
    void shouldPassCorrectInputToCreateUseCase() {
        // Arrange
        CreateUseCase.Output output = CreateUseCase.Output.builder()
                .game(game)
                .build();

        when(createUseCase.execute(any(CreateUseCase.Input.class))).thenReturn(output);

        // Act
        gameController.create(gameCreate);

        // Assert
        verify(createUseCase).execute(argThat(input ->
                input.getJwt().equals(jwt) &&
                        input.getGameCreate().equals(gameCreate)
        ));
    }

    @Test
    void shouldPassCorrectInputToFindByIdUseCase() {
        // Arrange
        FindByIdUseCase.Output output = FindByIdUseCase.Output.builder()
                .game(game)
                .build();

        when(findByIdUseCase.execute(any(FindByIdUseCase.Input.class))).thenReturn(output);

        // Act
        gameController.findById(gameId);

        // Assert
        verify(findByIdUseCase).execute(argThat(input ->
                input.getJwt().equals(jwt) &&
                        input.getId().equals(gameId)
        ));
    }

    @Test
    void shouldPassCorrectInputToUpdateUseCase() {
        // Arrange
        UpdateUseCase.Output output = UpdateUseCase.Output.builder()
                .game(game)
                .build();

        when(updateUseCase.execute(any(UpdateUseCase.Input.class))).thenReturn(output);

        // Act
        gameController.update(gameId, gameUpdate);

        // Assert
        verify(updateUseCase).execute(argThat(input ->
                input.getJwt().equals(jwt) &&
                        input.getId().equals(gameId) &&
                        input.getGameUpdate().equals(gameUpdate)
        ));
    }

    @Test
    void shouldPassCorrectInputToDeleteUseCase() {
        // Arrange
        doNothing().when(deleteUseCase).execute(any(DeleteUseCase.Input.class));

        // Act
        gameController.delete(gameId);

        // Assert
        verify(deleteUseCase).execute(argThat(input ->
                input.getJwt().equals(jwt) &&
                        input.getId().equals(gameId)
        ));
    }

    @Test
    void shouldThrowExceptionWhenDeleteThrowsException() {
        // Arrange
        doThrow(new RuntimeException("Delete failed")).when(deleteUseCase).execute(any(DeleteUseCase.Input.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> gameController.delete(gameId));
        assertEquals("Delete failed", exception.getMessage());

        verify(httpServletRequest).getHeader("Authorization");
        verify(deleteUseCase).execute(any(DeleteUseCase.Input.class));
    }

    @Test
    void shouldHandleWhenJwtIsNull() {
        // Arrange
        CreateUseCase.Output output = CreateUseCase.Output.builder()
                .game(game)
                .build();

        when(httpServletRequest.getHeader("Authorization")).thenReturn(null);
        when(createUseCase.execute(any(CreateUseCase.Input.class))).thenReturn(output);

        // Act
        ResponseEntity<Game> response = gameController.create(gameCreate);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(game, response.getBody());

        verify(httpServletRequest).getHeader("Authorization");
        verify(createUseCase).execute(argThat(input ->
                input.getJwt() == null &&
                        input.getGameCreate().equals(gameCreate)
        ));
    }
}
