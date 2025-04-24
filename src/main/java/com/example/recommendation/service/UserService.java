package com.example.recommendation.service;

import com.example.recommendation.pojo.dto.UserLoginDTO;
import com.example.recommendation.pojo.dto.UserHistoryDTO;
import com.example.recommendation.pojo.dto.UserScoreDTO;
import com.example.recommendation.pojo.entity.User;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.pojo.vo.UserHistoryListVO;

import java.util.List;

public interface UserService {
    User wxLogin(UserLoginDTO userLoginDTO);

    List<UserHistoryListVO> historyList(Integer id);

    void addHistory(UserHistoryDTO userHistoryDTO);

    void score(UserScoreDTO userScoreDTO);

    List<InfoCategoryListVO> search(Long id, String searchValue);
}
