package org.example.plugin.mininggame.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.example.plugin.mininggame.mapper.data.PlayerScore;

import java.util.List;

public interface PlayerScoreMapper {
    @Select("select * from player_score3")
    List<PlayerScore> selectList();

    @Insert("insert player_score3(count, score, registered_at) values (#{count}, #{score}, now())")
    int insert(PlayerScore playerScore);
}
