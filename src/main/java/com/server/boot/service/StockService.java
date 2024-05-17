package com.server.boot.service;

import com.server.boot.dao.InboundDAO;
import com.server.boot.dao.StockDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockDAO stockDAO;

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectStock(Map<String, String> map) {
        return stockDAO.selectStock(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectStockPrint() {
        return stockDAO.selectStockPrint();
    }

}
