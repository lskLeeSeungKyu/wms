package com.server.boot.controller;

import com.server.boot.dto.RestaurantDTO;
import com.server.boot.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RestaurantsController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurantEntry")
    public Map<String, String> restaurantEntry(@RequestBody RestaurantDTO restaurantDTO) {
        return restaurantService.restaurantEntry(restaurantDTO);
    }

    @PostMapping("/restaurantDelete")
    public Map<String, String> restaurantDelete(@RequestBody Map<String, Integer> map) {
        return restaurantService.restaurantDelete(map);
    }

    @GetMapping("/selectRestaurant")
    public RestaurantDTO selectRestaurant(@RequestParam int restaurantId) {
        return restaurantService.selectRestaurant(restaurantId);
    }

    @GetMapping("/selectRestaurantAll")
    public List<RestaurantDTO> selectRestaurantAll() {
        return restaurantService.selectRestaurantAll();
    }
}
