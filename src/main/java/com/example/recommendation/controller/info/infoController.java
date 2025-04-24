package com.example.recommendation.controller.info;

import com.example.recommendation.common.Result;
import com.example.recommendation.pojo.dto.InfoAddDTO;
import com.example.recommendation.pojo.vo.InfoCategoryListVO;
import com.example.recommendation.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/info")
@Api(tags = "农业信息")
@Slf4j
@CrossOrigin
public class infoController {
    @Autowired
    private InfoService infoService;

    //根据类型id获取信息列表
    @GetMapping("/category/list")
    @ApiOperation("根据类型id查询数据")
    public Result<List<InfoCategoryListVO>> getInfoCategoryList(Integer id){
        log.info("根据类型id获取信息列表");
        List<InfoCategoryListVO> infoCategoryListVO = infoService.getInfoCategoryList(id);
        return Result.success(infoCategoryListVO);
    }

    //随机获取数据
    @GetMapping("/random")
    @ApiOperation("随机获取数据")
    public Result<List<InfoCategoryListVO>> getRandomInfo(){
        log.info("随机获取数据");
        List<InfoCategoryListVO> infoCategoryListVO = infoService.getRandomInfo();
        return Result.success(infoCategoryListVO);
    }

    //新增加信息
    @PostMapping("/add")
    @ApiOperation("新增加信息")
    public Result<String> addInfo(@RequestBody InfoAddDTO infoAddDTO){
        log.info("新增加信息");
        infoService.addInfo(infoAddDTO);
        return Result.success();
    }
    //推荐数据
    @GetMapping("/recommend")
    @ApiOperation("推荐数据")
    public Result<List<InfoCategoryListVO>> recommend(Integer userId){
        log.info("推荐数据");
        List<InfoCategoryListVO> infoCategoryListVO = infoService.recommend(userId);
        return Result.success(infoCategoryListVO);
    }

}
