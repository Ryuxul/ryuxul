package com.example.recommendation.controller.user;


import com.example.recommendation.common.Alink.AlinkPredict;
import com.example.recommendation.common.Result;
import com.example.recommendation.common.constant.BaseContext;
import com.example.recommendation.common.constant.JwtClaimsConstant;
import com.example.recommendation.pojo.dto.UserHistoryDTO;
import com.example.recommendation.pojo.dto.UserLoginDTO;
import com.example.recommendation.pojo.dto.UserScoreDTO;
import com.example.recommendation.pojo.entity.User;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.pojo.vo.UserHistoryListVO;
import com.example.recommendation.pojo.vo.UserLoginVO;
import com.example.recommendation.properties.JwtProperties;
import com.example.recommendation.service.UserService;
import com.example.recommendation.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.types.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@Slf4j
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private KafkaTemplate<String, String> kafka;
    @Autowired
    private AlinkPredict alinkPredict;
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录:{}", userLoginDTO.getCode());
        // 调用service完成微信登录
        User user = userService.wxLogin(userLoginDTO);
        BaseContext.setCurrentId(user.getId());
        //生产jwt 令牌
        Map claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);
        UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();
        return Result.success(userLoginVO);
    }

    // 查看浏览记录
    @GetMapping("/history/list")
    @ApiOperation("查看浏览记录")
    public Result<List<UserHistoryListVO>> historyList(Integer id){
        log.info("查看浏览记录");
        List<UserHistoryListVO> userHistoryListVO = userService.historyList(id);
        return Result.success(userHistoryListVO);
    }

    //添加浏览记录
    @PostMapping("/history/add")
    @ApiOperation("添加浏览记录")
    public Result<String> addHistory(@RequestBody UserHistoryDTO userHistoryDTO){
        log.info("添加浏览记录");
        Long userId = userHistoryDTO.getId();
        Integer categoryId = userHistoryDTO.getCategoryId();
        String kafkaString = userId + "," + categoryId;
        kafka.send("first", kafkaString);
        userService.addHistory(userHistoryDTO);
        return Result.success("添加成功");
    }

    //用户评分数据
    @PostMapping("/score")
    @ApiOperation("用户评分数据")
    public Result<String> score(@RequestBody UserScoreDTO userScoreDTO){
        log.info("用户评分数据");
        Long userId = userScoreDTO.getId();
        Integer categoryId = userScoreDTO.getCategoryId();
        String kafkaString = userId + "," + categoryId;
        if (userScoreDTO.getScore() > 3){
            kafka.send("first", kafkaString);
        }
        userService.score(userScoreDTO);
        return Result.success("评分成功");
    }

    //用户搜索
    @GetMapping("/search")
    @ApiOperation("用户搜索")
    public Result<List<InfoCategoryListVO>> search(Long id, String searchValue) throws Exception {
        log.info("用户搜索");
        Row row = alinkPredict.predict(searchValue);
        String pre =  String.valueOf(row.getField(2));
        String kafkaString = id + "," + pre;
        kafka.send("first", kafkaString);
        List<InfoCategoryListVO> infoCategoryListVO = userService.search(id,searchValue);
        return Result.success(infoCategoryListVO);
    }




}
