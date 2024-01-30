package com.server.boot.dao;

import com.server.boot.dto.RestaurantDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RestaurantDAO {

    void restaurantEntry(RestaurantDTO restaurantDTO);

    RestaurantDTO selectRestaurant(int restaurantId);

    List<RestaurantDTO> selectRestaurantAll();
}

