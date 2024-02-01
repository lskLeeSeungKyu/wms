package com.server.boot.dao;

import com.server.boot.dto.VisitsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface VisitsDAO {

    void visitsEntry(VisitsDTO visitsDTO);

    void visitsDelete(Map<String, Integer> map);

    VisitsDTO selectVisits(int visitsId);

    List<VisitsDTO> selectVisitsAll();

}

