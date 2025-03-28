package com.tracktainment.gamemanager.dataprovider;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import com.tracktainment.gamemanager.exception.ResourceAlreadyExistsException;
import com.tracktainment.gamemanager.exception.ResourceNotFoundException;
import com.tracktainment.gamemanager.mapper.GameMapperDataProvider;
import com.tracktainment.gamemanager.repository.GameRepository;
import com.tracktainment.gamemanager.usecases.ListByCriteriaUseCase;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameDataProviderSql implements GameDataProvider {

    private final GameMapperDataProvider mapper;
    private final GameRepository gameRepository;
    private final EntityManager entityManager;

    @Override
    public Game create(GameCreate gameCreate) {
        if (existsByTitle(gameCreate.getTitle())) {
            throw new ResourceAlreadyExistsException(GameEntity.class, gameCreate.getTitle());
        }

        return mapper.toGame(gameRepository.save(mapper.toGameEntity(gameCreate)));
    }

    @Override
    public Game findById(Long id) {
        return mapper.toGame(findGameEntityById(id));
    }

    @Override
    public List<Game> listByCriteria(ListByCriteriaUseCase.Input input) {
        return null;
    }

    @Override
    public Game update(Long id, GameUpdate gameUpdate) {
        GameEntity gameEntity = findGameEntityById(id);
        mapper.updateBookEntity(gameEntity, gameUpdate);
        return mapper.toGame(gameRepository.save(gameEntity));
    }

    @Override
    public void delete(Long id) {
        if (existsById(id)) {
            gameRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(GameEntity.class, id.toString());
        }
    }

    private boolean existsByTitle(String title) {
        return gameRepository.findByTitle(title).isPresent();
    }

    private boolean existsById(Long id) {
        return gameRepository.findById(id).isPresent();
    }

    private GameEntity findGameEntityById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(GameEntity.class, id.toString())
                );
    }
}
