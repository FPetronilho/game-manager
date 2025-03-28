package com.tracktainment.gamemanager.controller;

import com.tracktainment.gamemanager.api.GameRestApi;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.usecases.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class GameController implements GameRestApi {

    private final CreateUseCase createUseCase;
    private final FindByIdUseCase findByIdUseCase;
    private final ListByCriteriaUseCase listByCriteriaUseCase;
    private final UpdateUseCase updateUseCase;
    private final DeleteUseCase deleteUseCase;

    @Override
    public ResponseEntity<Game> create(GameCreate gameCreate) {
        return null;
    }

    @Override
    public ResponseEntity<Game> findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<Game>> listByCriteria(Long offset, Long limit, String title, String platform, String genre, String developer, String releaseDate, String createdAt, String from, String to, List<OrderBy> orderByList, List<OrderDirection> orderDirectionList) {
        return null;
    }

    @Override
    public ResponseEntity<Game> update(Long id, GameUpdate gameUpdate) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}
