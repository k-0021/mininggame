package org.example.plugin.mininggame.mapper.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * プレイヤーのスコア情報を扱うオブジェクト
 * DBに存在するテーブルと連動する
 */

@Getter
@Setter
@NoArgsConstructor
public class PlayerScore {
    private int id;
    private int score;
    private LocalDateTime registeredAt;

    public PlayerScore(int score) {
        this.score = score;
    }
}

