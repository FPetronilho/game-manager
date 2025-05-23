package com.tracktainment.gamemanager.dataprovider;

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
import com.tracktainment.gamemanager.util.Constants;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameDataProviderSql implements GameDataProvider {

    private final GameMapperDataProvider mapper;
    private final GameRepository gameRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Game create(GameCreate gameCreate) {
        if (existsByTitle(gameCreate.getTitle())) {
            throw new ResourceAlreadyExistsException(GameEntity.class, gameCreate.getTitle());
        }

        return mapper.toGame(gameRepository.save(mapper.toGameEntity(gameCreate)));
    }

    @Override
    public Game findById(String id) {
        return mapper.toGame(findGameEntityById(id));
    }

    @Override
    public List<Game> listByCriteria(ListByCriteriaUseCase.Input input) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GameEntity> criteriaQuery = criteriaBuilder.createQuery(GameEntity.class);
        Root<GameEntity> root = criteriaQuery.from(GameEntity.class);

        Predicate[] predicates = buildPredicates(criteriaBuilder, root, input);
        criteriaQuery.where(predicates);

        applyListSorting(criteriaBuilder, criteriaQuery, root, input);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(input.getOffset() != null ? input.getOffset() : Constants.MIN_OFFSET)
                .setMaxResults(input.getLimit() != null ? input.getLimit() : Integer.parseInt(Constants.DEFAULT_LIMIT))
                .getResultList()
                .stream()
                .map(mapper::toGame)
                .toList();
    }

    @Override
    @Transactional
    public Game update(String id, GameUpdate gameUpdate) {
        GameEntity gameEntity = findGameEntityById(id);
        mapper.updateBookEntity(gameEntity, gameUpdate);
        return mapper.toGame(gameRepository.save(gameEntity));
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (existsById(id)) {
            gameRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(GameEntity.class, id);
        }
    }

    private boolean existsByTitle(String title) {
        return gameRepository.findByTitle(title).isPresent();
    }

    private boolean existsById(String id) {
        return gameRepository.findById(id).isPresent();
    }

    private GameEntity findGameEntityById(String id) {
        return gameRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(GameEntity.class, id)
                );
    }

    private Predicate[] buildPredicates(
            CriteriaBuilder criteriaBuilder,
            Root<GameEntity> root,
            ListByCriteriaUseCase.Input input
    ) {
        List<Predicate> predicates = new ArrayList<>();

        if (input.getIds() != null) {
            List<String> ids = List.of(input.getIds().split(","));
            predicates.add(criteriaBuilder.in(root.get("id")).value(ids));
        }

        if (input.getTitle() != null) {
            predicates.add(criteriaBuilder.like(
                    root.get("title"),
                    "%" + input.getTitle() + "%"
            ));
        }


        if (input.getPlatform() != null) {
            predicates.add(criteriaBuilder.like(
                    root.get("platform"),
                    "%" + input.getPlatform() + "%"
            ));
        }

        if (input.getGenre() != null) {
            predicates.add(criteriaBuilder.like(
                    root.get("genre"),
                    "%" + input.getGenre() + "%"
            ));
        }

        if (input.getDeveloper() != null) {
            predicates.add(criteriaBuilder.like(
                    root.get("developer"),
                    "%" + input.getDeveloper() + "%"
            ));
        }

        if (input.getReleaseDate() != null) {
            predicates.add(criteriaBuilder.equal(
                    root.get("releaseDate"),
                    input.getReleaseDate().atStartOfDay()
            ));
        }

        if (input.getCreatedAt() != null) {
            predicates.add(criteriaBuilder.between(
                    root.get("createdAt"),
                    input.getCreatedAt().atStartOfDay(),
                    input.getCreatedAt().atTime(23, 59, 59, 999999999)
            ));
        }

        if (input.getFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("createdAt"),
                    input.getFrom().atStartOfDay()));
        }

        if (input.getTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("createdAt"),
                    input.getTo().atTime(23, 59, 59, 999999999)
            ));
        }

        return predicates.toArray(new Predicate[0]);
    }

    private void applyListSorting(
            CriteriaBuilder criteriaBuilder,
            CriteriaQuery<GameEntity> criteriaQuery,
            Root<GameEntity> root,
            ListByCriteriaUseCase.Input input
    ) {
        if (input.getOrderByList() != null && input.getOrderDirectionList() != null) {
            List<Order> orderList = new ArrayList<>();
            for (int i=0; i<input.getOrderByList().size(); i++) {
                OrderBy orderBy = input.getOrderByList().get(i);
                OrderDirection orderDirection = input.getOrderDirectionList().get(i);

                if (orderBy != null && orderDirection != null) {
                    if (orderBy == OrderBy.TITLE) {
                        if (orderDirection == OrderDirection.ASC) {
                            orderList.add(criteriaBuilder.asc(root.get("title")));
                        } else {
                            orderList.add(criteriaBuilder.desc(root.get("title")));
                        }
                    }

                    if (orderBy == OrderBy.PLATFORM) {
                        if (orderDirection == OrderDirection.ASC) {
                            orderList.add(criteriaBuilder.asc(root.get("platform")));
                        } else {
                            orderList.add(criteriaBuilder.desc(root.get("platform")));
                        }
                    }

                    if (orderBy == OrderBy.GENRE) {
                        if (orderDirection == OrderDirection.ASC) {
                            orderList.add(criteriaBuilder.asc(root.get("genre")));
                        } else {
                            orderList.add(criteriaBuilder.desc(root.get("genre")));
                        }
                    }

                    if (orderBy == OrderBy.CREATED_AT) {
                        if (orderDirection == OrderDirection.ASC) {
                            orderList.add(criteriaBuilder.asc(root.get("createdAt")));
                        } else {
                            orderList.add(criteriaBuilder.desc(root.get("createdAt")));
                        }
                    }
                }
            }

            if (!orderList.isEmpty()) {
                criteriaQuery.orderBy(orderList);
            }
        }
    }
}
