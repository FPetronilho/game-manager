package com.tracktainment.gamemanager.api;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.domain.OrderBy;
import com.tracktainment.gamemanager.domain.OrderDirection;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;
import com.tracktainment.gamemanager.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("api/v1/games")
@Validated
@Tag(name = "Games", description = "Game management APIs")
public interface GameRestApi {

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Create a new game",
            description = "Creates a new game with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully",
                    content = @Content(schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Game already exists")
    })
    ResponseEntity<Game> create(
            @Parameter(description = "Game creation data", required = true)
            @RequestBody @Valid GameCreate gameCreate
    );

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find a game by ID",
            description = "Returns a game based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found",
                    content = @Content(schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    ResponseEntity<Game> findById(
            @Parameter(description = "Game ID", required = true)
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String id
    );

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "List games by criteria",
            description = "Returns a list of games filtered by various criteria"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of games",
                    content = @Content(schema = @Schema(implementation = Game.class)))
    })
    ResponseEntity<List<Game>> listByCriteria(
            @Parameter(description = "Result offset (pagination)")
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_OFFSET)
            @Min(value = Constants.MIN_OFFSET, message = Constants.OFFSET_INVALID_MSG) Integer offset,

            @Parameter(description = "Maximum number of results to return")
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_LIMIT)
            @Min(value = Constants.MIN_LIMIT, message = Constants.LIMIT_INVALID_MSG)
            @Max(value = Constants.MAX_LIMIT, message = Constants.LIMIT_INVALID_MSG) Integer limit,

            @Parameter(description = "Filter by IDs (comma-separated)")
            @RequestParam(required = false)
            @Pattern(regexp = Constants.ID_LIST_REGEX, message = Constants.IDS_INVALID_MSG) String ids,

            @Parameter(description = "Filter by title")
            @RequestParam(required = false)
            @Pattern(regexp = Constants.TITLE_REGEX, message = Constants.TITLE_INVALID_MSG) String title,

            @Parameter(description = "Filter by platform")
            @RequestParam(required = false)
            @Pattern(regexp = Constants.PLATFORM_REGEX, message = Constants.PLATFORM_INVALID_MSG) String platform,

            @Parameter(description = "Filter by genre")
            @RequestParam(required = false)
            @Pattern(regexp = Constants.GENRE_REGEX, message = Constants.GENRE_INVALID_MSG) String genre,

            @Parameter(description = "Filter by developer")
            @RequestParam(required = false)
            @Pattern(regexp = Constants.DEVELOPER_REGEX, message = Constants.DEVELOPER_INVALID_MSG) String developer,

            @Parameter(description = "Filter by release date")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate,

            @Parameter(description = "Filter by creation date")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,

            @Parameter(description = "Filter by date range start")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @Parameter(description = "Filter by date range end")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,

            @Parameter(description = "Order by fields")
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_ORDER) List<OrderBy> orderByList,

            @Parameter(description = "Order direction for each field")
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_DIRECTION) List<OrderDirection> orderDirectionList
    );

    @PatchMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Update a game",
            description = "Updates an existing game with the provided details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated successfully",
                    content = @Content(schema = @Schema(implementation = Game.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    ResponseEntity<Game> update(
            @Parameter(description = "Game ID", required = true)
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String id,

            @Parameter(description = "Game update data", required = true)
            @RequestBody @Valid GameUpdate gameUpdate
    );

    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "Delete a game",
            description = "Deletes a game based on its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Game ID", required = true)
            @PathVariable @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_INVALID_MSG) String id
    );
}
