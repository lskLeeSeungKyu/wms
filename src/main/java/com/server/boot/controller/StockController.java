package com.server.boot.controller;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.InboundService;
import com.server.boot.service.StockService;
import com.server.boot.service.UserService;
import com.server.boot.socket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://www.lsg-wms.site")
public class StockController {

    private final StockService stockService;


    @PostMapping("/selectStock")
    public List<Map<String, String>> selectStock(@RequestBody Map<String, String> map) {
        return stockService.selectStock(map);
    }

}
