package com.server.boot.dao;

import com.server.boot.dto.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RestaurantDAO {

    void restaurantEntry(RestaurantDTO restaurantDTO);

    void restaurantDelete(Map<String, Integer> map);

    RestaurantDTO selectRestaurant(int restaurantId);

    List<RestaurantDTO> selectRestaurantAll();

}

