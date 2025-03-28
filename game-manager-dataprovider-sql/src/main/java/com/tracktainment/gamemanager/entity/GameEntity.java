package com.tracktainment.gamemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "games")
public class GameEntity extends BaseEntity {

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "platform", nullable = false, length = 50)
    private String platform;

    @Column(name = "genre", length = 50)
    private String genre;

    @Column(name = "developer", length = 150)
    private String developer;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}
