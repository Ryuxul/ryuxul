package com.example.recommendation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.recommendation.common.constant.BaseContext;
import com.example.recommendation.common.constant.CategoryNameConstant;
import com.example.recommendation.common.constant.ImgUrlConstant;
import com.example.recommendation.exception.LoginFailedException;
import com.example.recommendation.mapper.InfoMapper;
import com.example.recommendation.mapper.UserMapper;
import com.example.recommendation.pojo.dto.UserHistoryDTO;
import com.example.recommendation.pojo.dto.UserLoginDTO;
import com.example.recommendation.pojo.dto.UserScoreDTO;
import com.example.recommendation.pojo.entity.AgricultureInfomation;
import com.example.recommendation.pojo.entity.User;
import com.example.recommendation.pojo.entity.UserHistory;
import com.example.recommendation.pojo.entity.UserScore;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.pojo.vo.UserHistoryListVO;
import com.example.recommendation.properties.WeChatProperties;
import com.example.recommendation.service.UserService;
import com.example.recommendation.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private InfoMapper infoMapper;
    @Override
    public User wxLogin(UserLoginDTO userLoginDTO) {
        //调用微信登录接口 获取openid
        String openid = getOpenid(userLoginDTO.getCode());
        //判断openid是否为空 如果为空 则说明登录失败 抛出异常
        if (openid == null) {
            throw new LoginFailedException("登录错误");
        }
        //判断当前用户是否为新用户
        User user = userMapper.getByOpenid(openid);
        //是新用户 则自动完成注册
        if (user == null) {
            user = User.builder().openid(openid).createTime(LocalDateTime.now()).build();
            userMapper.insert(user);
        }
        BaseContext.setCurrentId(user.getId());
        //返回这个用户信息
        return user;
    }

    @Override
    public List<UserHistoryListVO> historyList(Integer id) {
        List<UserHistory> userHistory = userMapper.historyList(id);
        List<UserHistoryListVO> list = new ArrayList<UserHistoryListVO>();
        for (UserHistory history : userHistory) {
            UserHistoryListVO vo = UserHistoryListVO.builder().agricultureInfomationTitle(history.getAgricultureInfomationTitle())
                    .detail(history.getDetail()).categoryId(history.getCategoryId())
                    .imgUrl(ImgUrlConstant.geturl(history.getCategoryId()))
                    .categoryName(CategoryNameConstant.getCategoryName(history.getCategoryId())).agricultureInfomationId(history.getAgricultureInfomationId()).build();
            list.add(vo);
        }
        return list;

    }


    // 添加浏览记录
    @Override
    public void addHistory(UserHistoryDTO userHistoryDTO) {
        UserHistory userHistory = UserHistory.builder().agricultureInfomationId(userHistoryDTO.getAgricultureInfomationId())
                .agricultureInfomationTitle(userHistoryDTO.getAgricultureInfomationTitle())
                .detail(userHistoryDTO.getDetail()).id(userHistoryDTO.getId()).browseTime(LocalDateTime.now())
                .categoryId(userHistoryDTO.getCategoryId()).build();
        userMapper.insertHistory(userHistory);
    }

    //用户评分数据
    @Override
    public void score(UserScoreDTO userScoreDTO) {
        //判断是否存在
        UserScore userScore1 = userMapper.scoreById(userScoreDTO.getAgricultureInfomationId());
        if (userScore1 == null){
            UserScore userScore = UserScore.builder().agricultureInfomationId(userScoreDTO.getAgricultureInfomationId())
                    .agricultureInfomationTitle(userScoreDTO.getAgricultureInfomationTitle())
                    .score(userScoreDTO.getScore())
                    .categoryId(userScoreDTO.getCategoryId())
                    .detail(userScoreDTO.getDetail())
                    .id(userScoreDTO.getId())
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now()).build();
            userMapper.insertScore(userScore);
        }
        else {
            userMapper.updateScore(userScoreDTO.getAgricultureInfomationId(),userScoreDTO.getScore(),LocalDateTime.now());
        }

    }


    // 用户搜索
    @Override
    public List<InfoCategoryListVO> search(Long id, String searchValue) {
        //进行标题模糊查询  如果为空，则对详情内容模糊查询
       List<AgricultureInfomation> list =  userMapper.searchTitle(searchValue);

       if (list.size() == 0){
           list = userMapper.searchDetail(searchValue);
       }
        List<InfoCategoryListVO> infoCategoryListVO = new ArrayList<>();
        for (AgricultureInfomation agricultureInfomation : list) {
            Long count = infoMapper.getCount(agricultureInfomation.getCategoryId());
            Long hot = infoMapper.getHot(agricultureInfomation.getCategoryId());
            InfoCategoryListVO build = InfoCategoryListVO.builder().agricultureInfomationId(agricultureInfomation.getAgricultureInfomationId())
                    .count(count).hot(hot).detail(agricultureInfomation.getDetail())
                    .agricultureInfomationTitle(agricultureInfomation.getAgricultureInfomationTitle())
                    .categoryId(agricultureInfomation.getCategoryId())
                    .categoryName(CategoryNameConstant.getCategoryName(agricultureInfomation.getCategoryId()))
                    .imgUrl(ImgUrlConstant.geturl(agricultureInfomation.getCategoryId())).build();
            infoCategoryListVO.add(build);
        }
        //将搜索数据进行保存
        userMapper.insertSearchValue(id,searchValue);

        return infoCategoryListVO;

    }

    public String getOpenid(String code) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN_URL, map);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }

}
