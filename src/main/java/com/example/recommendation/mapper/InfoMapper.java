package com.example.recommendation.mapper;


import com.example.recommendation.pojo.entity.AgricultureInfomation;
import com.example.recommendation.pojo.entity.FlinkResult;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface InfoMapper {

    @Select("select * from agriculture_infomation where category_id=2 order by rand() limit 20")
    List<AgricultureInfomation> getInfoCategoryList(Integer id);

    @Select("select count(*) from agriculture_infomation where category_id=#{id}")
    Long getCount(Integer id);

    @Select("select count(*) from user_history where category_id=#{id}")
    Long getHot(Integer id);

    @Select("select * from agriculture_infomation order by rand() limit 20")
    List<AgricultureInfomation> getInfoRandList();


    @Insert("insert into agriculture_infomation(agriculture_infomation_title,category_id,create_time,detail) values(#{agricultureInfomationTitle},#{categoryId},#{createTime},#{detail})")
    void insert(AgricultureInfomation build);

    List<FlinkResult> maxWeight(Integer userId);

    @Select("select * from agriculture_infomation where category_id=#{categoryId} order by rand() limit #{limit}")
    List<AgricultureInfomation> getRecommendInfoCategoryList(Integer categoryId, Integer limit);
}
