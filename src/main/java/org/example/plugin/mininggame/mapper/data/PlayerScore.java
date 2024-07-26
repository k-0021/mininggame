package org.example.plugin.mininggame.mapper.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PlayerScore {
    private int id;
    private int count;
    private int score;
    private LocalDateTime registered_at;

    public PlayerScore(int count,int score) {
        this.count = count;
        this.score = score;
    }
}

