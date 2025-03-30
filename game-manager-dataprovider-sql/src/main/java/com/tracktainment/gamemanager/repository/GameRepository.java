package com.tracktainment.gamemanager.repository;

import com.tracktainment.gamemanager.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Optional<GameEntity> findByTitle(String title);

    Optional<GameEntity> findById(String id);

    void deleteById(String id);
}
