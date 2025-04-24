package com.example.recommendation.mapper;

import com.example.recommendation.pojo.entity.AgricultureInfomation;
import com.example.recommendation.pojo.entity.User;
import com.example.recommendation.pojo.entity.UserHistory;
import com.example.recommendation.pojo.entity.UserScore;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.pojo.vo.UserHistoryListVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid=#{openid}")
    User getByOpenid(String openid);

    void insert(User user);

    @Select("select * from user_history where id=#{id} order by browse_time desc")
    List<UserHistory> historyList(Integer id);

    @Insert("insert into user_history(id,agriculture_infomation_id,agriculture_infomation_title,category_id,detail,browse_time)" +
            " values(#{id},#{agricultureInfomationId},#{agricultureInfomationTitle},#{categoryId},#{detail},#{browseTime})")
    void insertHistory(UserHistory userHistory);

    @Select("select * from user_score where agriculture_infomation_id=#{agricultureInfomationId}")
    UserScore scoreById(Integer agricultureInfomationId);

    @Update("update user_score set score=#{score},update_time=#{now} where agriculture_infomation_id=#{agricultureInfomationId}")
    void updateScore(Integer agricultureInfomationId, Integer score, LocalDateTime now);

    @Insert("insert into user_score(id,agriculture_infomation_id,score,category_id,agriculture_infomation_title,detail,create_time,update_time)" +
    "values (#{id},#{agricultureInfomationId},#{score},#{categoryId},#{agricultureInfomationTitle},#{detail},#{createTime},#{updateTime})"
    )
    void insertScore(UserScore userScore);

    @Select("select * from agriculture_infomation where agriculture_infomation_title like concat('%',#{searchValue},'%')")
    List<AgricultureInfomation> searchTitle(String searchValue);

    @Select("select * from agriculture_infomation where detail like concat('%',#{searchValue},'%'")
    List<AgricultureInfomation> searchDetail(String searchValue);

    @Insert("insert into user_search(id,search_value) values(#{id},#{searchValue})")
    void insertSearchValue(Long id, String searchValue);
}
