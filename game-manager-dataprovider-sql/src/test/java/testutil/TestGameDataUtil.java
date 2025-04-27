package testutil;

import com.tracktainment.gamemanager.domain.Game;
import com.tracktainment.gamemanager.dto.GameCreate;
import com.tracktainment.gamemanager.dto.GameUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestGameDataUtil {

    public static GameCreate createTestGameCreate() {
        return GameCreate.builder()
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .build();
    }

    public static GameUpdate createTestGameUpdate() {
        return GameUpdate.builder()
                .title("The Last of Us Part II: Remastered")
                .platform("PlayStation 5")
                .genre("Action Adventure")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2021, 6, 19))
                .build();
    }

    public static Game createTestGame() {
        return Game.builder()
                .id(UUID.randomUUID().toString())
                .title("The Last of Us Part II")
                .platform("PlayStation 5")
                .genre("Action")
                .developer("Naughty Dog")
                .releaseDate(LocalDate.of(2020, 6, 19))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
