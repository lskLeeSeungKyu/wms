package com.server.boot.service;

import com.server.boot.dao.RestaurantDAO;
import com.server.boot.dto.RestaurantDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;

    @Transactional
    public Map<String,String> restaurantEntry(RestaurantDTO restaurantDTO) {
        restaurantDAO.restaurantEntry(restaurantDTO);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @Transactional(readOnly = true)
    public RestaurantDTO selectRestaurant(int restaurantId) {
        return restaurantDAO.selectRestaurant(restaurantId);
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> selectRestaurantAll() {
        return restaurantDAO.selectRestaurantAll();
    }
}
