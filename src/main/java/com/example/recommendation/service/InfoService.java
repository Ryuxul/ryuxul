package com.example.recommendation.service;

import com.example.recommendation.pojo.dto.InfoAddDTO;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;

import java.util.List;

public interface InfoService {
    List<InfoCategoryListVO> getInfoCategoryList(Integer id);

    List<InfoCategoryListVO> getRandomInfo();

    void addInfo(InfoAddDTO infoAddDTO);

    List<InfoCategoryListVO> recommend(Integer userId);
}
