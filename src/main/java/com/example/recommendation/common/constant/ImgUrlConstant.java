package com.example.recommendation.common.constant;

import java.util.HashMap;
import java.util.Map;

public class ImgUrlConstant {
    public static String geturl(Integer categoryId){
        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(1,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%90%84%E5%9C%B0%E5%8A%A8%E6%80%81.png");
        map.put(2,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E6%83%A0%E5%86%9C%E6%94%BF%E7%AD%96.png");
        map.put(3,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%86%9C%E6%9C%BA.png");
        map.put(4,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%86%9C%E5%93%81%E5%AE%89%E5%85%A8.jpg");
        map.put(5,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%86%9C%E4%B8%9A%E7%A7%91%E6%8A%80.png");
        map.put(6,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%86%9C%E8%B5%84.png");
        map.put(7,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%B8%82%E5%9C%BA%E5%8A%A8%E6%80%81.png");
        map.put(8,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E7%8E%B0%E4%BB%A3%E6%9E%9C%E4%B8%9A.png");
        map.put(9,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E4%B9%A1%E6%9D%91%E6%8C%AF%E5%85%B4.png");
        map.put(10,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E7%A7%8D%E6%A4%8D.png");
        map.put(11,"https://shop-item-alan.oss-cn-beijing.aliyuncs.com/%E5%86%9C%E4%B8%9A%E5%9B%BE%E7%89%87/%E5%85%BB%E6%AE%96.png");
        return map.get(categoryId);
    }
}
