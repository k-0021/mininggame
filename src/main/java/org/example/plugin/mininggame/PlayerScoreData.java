package org.example.plugin.mininggame;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.plugin.mininggame.mapper.PlayerScoreMapper;
import org.example.plugin.mininggame.mapper.data.PlayerScore;

import java.io.InputStream;
import java.util.List;

public class PlayerScoreData {
    private final SqlSessionFactory sqlSessionFactory;

    public PlayerScoreData() {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<PlayerScore> selectList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            PlayerScoreMapper mapper = session.getMapper(PlayerScoreMapper.class);
            return mapper.selectList();
        }
    }

    public void insert(PlayerScore playerScore) {
        try (SqlSession session =  sqlSessionFactory.openSession(true)) {
            PlayerScoreMapper mapper = session.getMapper(PlayerScoreMapper.class);
            mapper.insert(playerScore);
        }
    }
}
