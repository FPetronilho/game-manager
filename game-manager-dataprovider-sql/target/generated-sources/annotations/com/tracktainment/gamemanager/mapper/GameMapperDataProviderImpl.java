package com.tracktainment.gamemanager.mapper;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.entity.GameEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-05T00:37:26+0200",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class GameMapperDataProviderImpl implements GameMapperDataProvider {

    @Override
    public Game toGame(GameEntity gameEntity) {
        if ( gameEntity == null ) {
            return null;
        }

        Game.GameBuilder game = Game.builder();

        game.id( gameEntity.getId() );
        game.title( gameEntity.getTitle() );
        game.platform( gameEntity.getPlatform() );
        game.genre( gameEntity.getGenre() );
        game.developer( gameEntity.getDeveloper() );
        game.releaseDate( gameEntity.getReleaseDate() );
        game.createdAt( gameEntity.getCreatedAt() );
        game.updatedAt( gameEntity.getUpdatedAt() );

        return game.build();
    }

    @Override
    public GameEntity toGameEntity(GameCreate gameCreate) {
        if ( gameCreate == null ) {
            return null;
        }

        GameEntity.GameEntityBuilder<?, ?> gameEntity = GameEntity.builder();

        gameEntity.title( gameCreate.getTitle() );
        gameEntity.platform( gameCreate.getPlatform() );
        gameEntity.genre( gameCreate.getGenre() );
        gameEntity.developer( gameCreate.getDeveloper() );
        gameEntity.releaseDate( gameCreate.getReleaseDate() );

        gameEntity.id( java.util.UUID.randomUUID().toString() );

        return gameEntity.build();
    }

    @Override
    public void updateBookEntity(GameEntity gameEntity, GameUpdate gameUpdate) {
        if ( gameUpdate == null ) {
            return;
        }

        if ( gameUpdate.getTitle() != null ) {
            gameEntity.setTitle( gameUpdate.getTitle() );
        }
        if ( gameUpdate.getPlatform() != null ) {
            gameEntity.setPlatform( gameUpdate.getPlatform() );
        }
        if ( gameUpdate.getGenre() != null ) {
            gameEntity.setGenre( gameUpdate.getGenre() );
        }
        if ( gameUpdate.getDeveloper() != null ) {
            gameEntity.setDeveloper( gameUpdate.getDeveloper() );
        }
        if ( gameUpdate.getReleaseDate() != null ) {
            gameEntity.setReleaseDate( gameUpdate.getReleaseDate() );
        }
    }
}
