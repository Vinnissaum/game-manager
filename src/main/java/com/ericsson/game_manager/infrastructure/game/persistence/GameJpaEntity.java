package com.ericsson.game_manager.infrastructure.game.persistence;

import com.ericsson.game_manager.domain.game.Game;
import com.ericsson.game_manager.domain.game.GameID;
import com.ericsson.game_manager.domain.publisher.Publisher;
import com.ericsson.game_manager.domain.publisher.PublisherID;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Map;

@Entity(name = "Game")
@Table(name = "game")
public class GameJpaEntity {

    @Id
    private String id;

    @Column(name = "publisher_id", nullable = false, length = 10)
    private String publisherId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @ElementCollection
    @CollectionTable(name = "time_played", //
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id") //
    )
    @MapKeyColumn(name = "date")
    @Column(name = "time")
    private Map<LocalDate, Integer> timePlayed;

    public GameJpaEntity() {
    }

    private GameJpaEntity( //
                           final String id, //
                           final String publisherId, //
                           final String name, //
                           final Map<LocalDate, Integer> timePlayed //
    ) {
        this.id = id;
        this.publisherId = publisherId;
        this.name = name;
        this.timePlayed = timePlayed;
    }

    public static GameJpaEntity from(final Game game) {
        return new GameJpaEntity( //
                game.getId().getValue(), //
                game.getPublisher().getId().getValue(), //
                game.getName(), //
                game.getTimePlayed() //
        );
    }

    public Game toAggregate() {
        return Game.with( //
                GameID.from(getId()), //
                Publisher.with(PublisherID.from(getPublisherId())), //
                getName(), //
                getTimePlayed() //
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<LocalDate, Integer> getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Map<LocalDate, Integer> timePlayed) {
        this.timePlayed = timePlayed;
    }
}
