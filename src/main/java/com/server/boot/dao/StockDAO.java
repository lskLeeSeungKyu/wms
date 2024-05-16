package com.server.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StockDAO {

    List<Map<String, String>> selectStock(Map<String, String> map);

}
