package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = UUID.class
)
public interface GameMapperDataProvider {

    Game toGame(GameEntity gameEntity);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "dbId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    GameEntity toGameEntity(GameCreate gameCreate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dbId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateBookEntity(
            @MappingTarget GameEntity gameEntity,
            GameUpdate gameUpdate
    );
}
