package com.example.recommendation.service.impl;

import com.example.recommendation.common.constant.CategoryNameConstant;
import com.example.recommendation.common.constant.ImgUrlConstant;
import com.example.recommendation.mapper.InfoMapper;
import com.example.recommendation.pojo.dto.InfoAddDTO;
import com.example.recommendation.pojo.entity.AgricultureInfomation;
import com.example.recommendation.pojo.entity.FlinkResult;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;

    @Override
    public List<InfoCategoryListVO> getInfoCategoryList(Integer id) {
        //根据类型id查询信息
        List<AgricultureInfomation> agricultureInfomation = infoMapper.getInfoCategoryList(id);
        List<InfoCategoryListVO> list = new ArrayList<InfoCategoryListVO>();
        for (AgricultureInfomation infomation : agricultureInfomation) {
            //根据信息id查询信息数量
            Long count = infoMapper.getCount(id);
            //根据信息id查询热度
            Long hot = infoMapper.getHot(id);
            InfoCategoryListVO infoCategory = InfoCategoryListVO.builder().agricultureInfomationTitle(infomation.getAgricultureInfomationTitle())
                    .categoryId(infomation.getCategoryId()).detail(infomation.getDetail())
                    .imgUrl(ImgUrlConstant.geturl(infomation.getCategoryId())).categoryName(CategoryNameConstant.getCategoryName(infomation.getCategoryId()))
                    .count(count).hot(hot).agricultureInfomationId(infomation.getAgricultureInfomationId()).build();
            list.add(infoCategory);
        }
        return list;
    }

    @Override
    public List<InfoCategoryListVO> getRandomInfo() {
        List<AgricultureInfomation> agricultureInfomation = infoMapper.getInfoRandList();
        List<InfoCategoryListVO> list = new ArrayList<InfoCategoryListVO>();
        for (AgricultureInfomation infomation : agricultureInfomation) {
            //根据信息id查询信息数量
            Long count = infoMapper.getCount(infomation.getCategoryId());
            //根据信息id查询热度
            Long hot = infoMapper.getHot(infomation.getCategoryId());
            InfoCategoryListVO infoCategory = InfoCategoryListVO.builder().agricultureInfomationTitle(infomation.getAgricultureInfomationTitle())
                    .categoryId(infomation.getCategoryId()).detail(infomation.getDetail())
                    .imgUrl(ImgUrlConstant.geturl(infomation.getCategoryId())).categoryName(CategoryNameConstant.getCategoryName(infomation.getCategoryId()))
                    .count(count).hot(hot).agricultureInfomationId(infomation.getAgricultureInfomationId()).build();
            list.add(infoCategory);
        }
        return list;
    }

    @Override
    public void addInfo(InfoAddDTO infoAddDTO) {
        AgricultureInfomation build = AgricultureInfomation.builder().agricultureInfomationTitle(infoAddDTO.getTitle())
                .detail(infoAddDTO.getDetail())
                .categoryId(infoAddDTO.getCategoryId())
                .createTime(LocalDateTime.now()).build();
        infoMapper.insert(build);
    }

    // 推荐部分
    @Override
    public List<InfoCategoryListVO> recommend(Integer userId) {
        // 根据userId查询Flink的处理结果 查询每个用户的flink计算结果 返回每个category中weight最大的数据。
    List<FlinkResult> flinkResults =  infoMapper.maxWeight(userId);
    Map<Integer, Integer> weightMap = new HashMap<>();
        int weight = flinkResults.stream().mapToInt(FlinkResult::getWeight).sum();
        for (FlinkResult flinkResult : flinkResults) {
            weightMap.put(flinkResult.getCategoryId(), flinkResult.getWeight()*20/weight);
        }
        //根据权重查询每个category的数据
        List<AgricultureInfomation> info = new ArrayList<>();
        for (Integer categoryId : weightMap.keySet()) {
            List<AgricultureInfomation> infoCategoryList = infoMapper.getRecommendInfoCategoryList(categoryId, weightMap.get(categoryId));
            info.addAll(infoCategoryList);
        }
        List<InfoCategoryListVO> infoCategoryListVO = new ArrayList<>();
        for (AgricultureInfomation agricultureInfomation : info) {
            Long count = infoMapper.getCount(agricultureInfomation.getCategoryId());
            Long hot = infoMapper.getHot(agricultureInfomation.getCategoryId());
            InfoCategoryListVO infoCategory = InfoCategoryListVO.builder().agricultureInfomationTitle(agricultureInfomation.getAgricultureInfomationTitle())
                    .categoryId(agricultureInfomation.getCategoryId())
                    .detail(agricultureInfomation.getDetail())
                    .imgUrl(ImgUrlConstant.geturl(agricultureInfomation.getCategoryId()))
                    .categoryName(CategoryNameConstant.getCategoryName(agricultureInfomation.getCategoryId()))
                    .count(count).hot(hot)
                    .agricultureInfomationId(agricultureInfomation.getAgricultureInfomationId()).build();
            infoCategoryListVO.add(infoCategory);
        }
        Collections.shuffle(infoCategoryListVO);
        return infoCategoryListVO;
    }
}
