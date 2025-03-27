package com.tracktainment.gamemanager.dataprovider;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.usecases.ListByCriteriaUseCase;

import java.util.List;

public interface GameDataProvider {

    Game create(GameCreate gameCreate);

    Game findById(Long id);

    List<Game> listByCriteria(ListByCriteriaUseCase.Input input);

    Game update(Long id, GameUpdate gameUpdate);

    void delete(Long id);
}
