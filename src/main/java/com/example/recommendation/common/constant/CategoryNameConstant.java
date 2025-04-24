package com.example.recommendation.common.constant;

import java.util.HashMap;
import java.util.Map;

public class CategoryNameConstant {
    public static void main(String[] args) {
        System.out.println(getCategoryName(1));
    }
    public static String getCategoryName(Integer categoryId){
        Map<Integer,String> map = new HashMap<Integer,String>();
        map.put(1,"各地动态");
        map.put(2,"惠农政策");
        map.put(3,"农机");
        map.put(4,"农品安全");
        map.put(5,"农业科技");
        map.put(6,"农资");
        map.put(7,"市场动态");
        map.put(8,"现代果业");
        map.put(9,"乡村振兴");
        map.put(10,"养殖");
        map.put(11,"种植");
        return map.get(categoryId);
    }
}
