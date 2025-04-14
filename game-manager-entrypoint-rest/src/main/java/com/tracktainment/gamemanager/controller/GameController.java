package com.tracktainment.gamemanager.controller;

import com.tracktainment.gamemanager.api.GameRestApi;
import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.exception.ParameterValidationFailedException;
import com.tracktainment.gamemanager.usecases.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    private final HttpServletRequest httpServletRequest;

    @Override
    public ResponseEntity<Game> create(GameCreate gameCreate) {
        log.info("Creating game: {}", gameCreate);
        String jwt = httpServletRequest.getHeader("Authorization");

        CreateUseCase.Input input = CreateUseCase.Input.builder()
                .jwt(jwt)
                .gameCreate(gameCreate)
                .build();

        CreateUseCase.Output output = createUseCase.execute(input);
        return new ResponseEntity<>(output.getGame(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Game> findById(String id) {
        log.info("Finding game by ID: {}", id);
        String jwt = httpServletRequest.getHeader("Authorization");

        FindByIdUseCase.Input input = FindByIdUseCase.Input.builder()
                .jwt(jwt)
                .id(id)
                .build();

        FindByIdUseCase.Output output = findByIdUseCase.execute(input);
        return new ResponseEntity<>(output.getGame(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Game>> listByCriteria(
            Integer offset,
            Integer limit,
            String ids,
            String title,
            String platform,
            String genre,
            String developer,
            LocalDate releaseDate,
            LocalDate createdAt,
            LocalDate from,
            LocalDate to,
            List<OrderBy> orderByList,
            List<OrderDirection> orderDirectionList
    ) {
        // Input treatment
        if (createdAt != null) {
            to = null;
            from = null;
        }

        // Input validation
        if (to != null && from != null && to.isBefore(from)) {
            throw new ParameterValidationFailedException("Invalid dates input: 'to' must be later than 'from'.");
        }

        if (orderByList.size() != orderDirectionList.size()) {
            throw new ParameterValidationFailedException(
                    String.format(
                            "Invalid orderBy and orderDirection pair. " +
                                    "'orderBy' size is %s and orderDirection size is %s. Both sizes must match",
                            orderByList.size(),
                            orderDirectionList.size()
                    )
            );
        }

        // Get jwt
        String jwt = httpServletRequest.getHeader("Authorization");

        ListByCriteriaUseCase.Input input = ListByCriteriaUseCase.Input.builder()
                .jwt(jwt)
                .offset(offset)
                .limit(limit)
                .ids(ids)
                .title(title)
                .platform(platform)
                .genre(genre)
                .developer(developer)
                .releaseDate(releaseDate)
                .createdAt(createdAt)
                .from(from)
                .to(to)
                .orderByList(orderByList)
                .orderDirectionList(orderDirectionList)
                .build();

        log.info("Listing games by criteria: {}", input);
        ListByCriteriaUseCase.Output output = listByCriteriaUseCase.execute(input);
        return new ResponseEntity<>(output.getGames(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Game> update(String id, GameUpdate gameUpdate) {
        log.info("Updating game by ID: {}. Updating to: {}", id, gameUpdate);
        String jwt = httpServletRequest.getHeader("Authorization");

        UpdateUseCase.Input input = UpdateUseCase.Input.builder()
                .jwt(jwt)
                .id(id)
                .gameUpdate(gameUpdate)
                .build();

        UpdateUseCase.Output output = updateUseCase.execute(input);
        return new ResponseEntity<>(output.getGame(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        log.info("Deleting game by ID: {}", id);
        String jwt = httpServletRequest.getHeader("Authorization");

        DeleteUseCase.Input input = DeleteUseCase.Input.builder()
                .jwt(jwt)
                .id(id)
                .build();

        deleteUseCase.execute(input);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
