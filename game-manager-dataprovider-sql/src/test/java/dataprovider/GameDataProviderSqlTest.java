package dataprovider;

import com.tracktainment.gamemanager.dataprovider.GameDataProviderSql;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import com.tracktainment.gamemanager.exception.ResourceAlreadyExistsException;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.mapper.GameMapperDataProvider;
import com.tracktainment.gamemanager.repository.GameRepository;
import com.tracktainment.gamemanager.usecases.ListByCriteriaUseCase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameDataProviderSqlTest {

    @Mock
    private GameMapperDataProvider mapper;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Mock
    private CriteriaQuery<GameEntity> criteriaQuery;

    @Mock
    private Root<GameEntity> root;

    @Mock
    private TypedQuery<GameEntity> typedQuery;

    @InjectMocks
    private GameDataProviderSql gameDataProviderSql;

    private GameCreate gameCreate;
    private GameUpdate gameUpdate;
    private Game game;
    private GameEntity gameEntity;
    private String gameId;

    @BeforeEach
    void setUp() {
        gameId = UUID.randomUUID().toString();
        gameCreate = TestGameDataUtil.createTestGameCreate();
        gameUpdate = TestGameDataUtil.createTestGameUpdate();
        game = TestGameDataUtil.createTestGame();

        gameEntity = GameEntity.builder()
                .id(gameId)
                .title(gameCreate.getTitle())
                .platform(gameCreate.getPlatform())
                .genre(gameCreate.getGenre())
                .developer(gameCreate.getDeveloper())
                .releaseDate(gameCreate.getReleaseDate())
                .build();
    }

    @Test
    void shouldCreateGameSuccessfully() {
        // Arrange
        when(gameRepository.findByTitle(gameCreate.getTitle())).thenReturn(Optional.empty());
        when(mapper.toGameEntity(gameCreate)).thenReturn(gameEntity);
        when(gameRepository.save(gameEntity)).thenReturn(gameEntity);
        when(mapper.toGame(gameEntity)).thenReturn(game);

        // Act
        Game result = gameDataProviderSql.create(gameCreate);

        // Assert
        assertNotNull(result);
        assertEquals(game, result);

        verify(gameRepository).findByTitle(gameCreate.getTitle());
        verify(mapper).toGameEntity(gameCreate);
        verify(gameRepository).save(gameEntity);
        verify(mapper).toGame(gameEntity);
    }

    @Test
    void shouldThrowResourceAlreadyExistsException() {
        // Arrange
        when(gameRepository.findByTitle(gameCreate.getTitle())).thenReturn(Optional.of(gameEntity));

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> gameDataProviderSql.create(gameCreate));

        verify(gameRepository).findByTitle(gameCreate.getTitle());
        verify(mapper, never()).toGameEntity(any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    void shouldFindGameByIdSuccessfully() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(mapper.toGame(gameEntity)).thenReturn(game);

        // Act
        Game result = gameDataProviderSql.findById(gameId);

        // Assert
        assertNotNull(result);
        assertEquals(game, result);

        verify(gameRepository).findById(gameId);
        verify(mapper).toGame(gameEntity);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenGameNotFound() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> gameDataProviderSql.findById(gameId));

        verify(gameRepository).findById(gameId);
        verify(mapper, never()).toGame(any());
    }

    @Test
    void shouldUpdateGameSuccessfully() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        when(gameRepository.save(gameEntity)).thenReturn(gameEntity);
        when(mapper.toGame(gameEntity)).thenReturn(game);

        // Act
        Game result = gameDataProviderSql.update(gameId, gameUpdate);

        // Assert
        assertNotNull(result);
        assertEquals(game, result);

        verify(gameRepository).findById(gameId);
        verify(mapper).updateBookEntity(gameEntity, gameUpdate);
        verify(gameRepository).save(gameEntity);
        verify(mapper).toGame(gameEntity);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistingGame() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> gameDataProviderSql.update(gameId, gameUpdate));

        verify(gameRepository).findById(gameId);
        verify(mapper, never()).updateBookEntity(any(), any());
        verify(gameRepository, never()).save(any());
    }

    @Test
    void shouldDeleteGameSuccessfully() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.of(gameEntity));
        doNothing().when(gameRepository).deleteById(gameId);

        // Act
        assertDoesNotThrow(() -> gameDataProviderSql.delete(gameId));

        // Assert
        verify(gameRepository).findById(gameId);
        verify(gameRepository).deleteById(gameId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistingGame() {
        // Arrange
        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> gameDataProviderSql.delete(gameId));

        verify(gameRepository).findById(gameId);
        verify(gameRepository, never()).deleteById((String) any());
    }

    @Test
    void shouldListGamesByCriteriaSuccessfully() {
        // Arrange
        List<GameEntity> gameEntities = Collections.singletonList(gameEntity);

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .offset(0)
                .limit(10)
                .orderByList(Collections.singletonList(OrderBy.TITLE))
                .orderDirectionList(Collections.singletonList(OrderDirection.ASC))
                .build();

        // Setup only the mocks that are actually used in this test
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(GameEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(GameEntity.class)).thenReturn(root);
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(gameEntities);

        // For ordering logic
        Path<Object> pathMock = mock(Path.class);
        when(root.get("title")).thenReturn(pathMock);
        when(criteriaBuilder.asc(pathMock)).thenReturn(mock(Order.class));
        when(criteriaQuery.orderBy(anyList())).thenReturn(criteriaQuery);

        when(mapper.toGame(gameEntity)).thenReturn(game);

        // Act
        List<Game> results = gameDataProviderSql.listByCriteria(input);

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(game, results.get(0));

        // Verify all the mocks were used
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(GameEntity.class);
        verify(criteriaQuery).from(GameEntity.class);
        verify(criteriaQuery).where(any(Predicate[].class));
        verify(entityManager).createQuery(criteriaQuery);
        verify(typedQuery).setFirstResult(anyInt());
        verify(typedQuery).setMaxResults(anyInt());
        verify(typedQuery).getResultList();
        verify(mapper).toGame(gameEntity);
    }

    @Test
    void shouldReturnEmptyListWhenNoGamesMatch() {
        // Arrange
        List<GameEntity> gameEntities = Collections.emptyList();

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .offset(0)
                .limit(10)
                .build();

        // Setup only the mocks that are actually used in this test
        when(entityManager.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(GameEntity.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(GameEntity.class)).thenReturn(root);
        when(criteriaQuery.where(any(Predicate[].class))).thenReturn(criteriaQuery);
        when(entityManager.createQuery(criteriaQuery)).thenReturn(typedQuery);
        when(typedQuery.setFirstResult(anyInt())).thenReturn(typedQuery);
        when(typedQuery.setMaxResults(anyInt())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(gameEntities);

        // Act
        List<Game> results = gameDataProviderSql.listByCriteria(input);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());

        // Verify all the mocks were used
        verify(entityManager).getCriteriaBuilder();
        verify(criteriaBuilder).createQuery(GameEntity.class);
        verify(criteriaQuery).from(GameEntity.class);
        verify(criteriaQuery).where(any(Predicate[].class));
        verify(entityManager).createQuery(criteriaQuery);
        verify(typedQuery).setFirstResult(anyInt());
        verify(typedQuery).setMaxResults(anyInt());
        verify(typedQuery).getResultList();
        verify(mapper, never()).toGame(any());
    }
}
