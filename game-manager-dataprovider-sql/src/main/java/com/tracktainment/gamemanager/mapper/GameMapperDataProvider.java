package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GameMapperDataProvider {

    Game toGame(GameEntity gameEntity);

    GameEntity toGameEntity(GameCreate gameCreate);

    void updateBookEntity(
            @MappingTarget GameEntity gameEntity,
            GameUpdate gameUpdate
    );
}
