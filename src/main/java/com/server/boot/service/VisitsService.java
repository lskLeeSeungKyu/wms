package com.server.boot.service;

import com.server.boot.dao.VisitsDAO;
import com.server.boot.dto.VisitsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VisitsService {

    private final VisitsDAO visitsDAO;

    @Transactional
    public Map<String,String> visitsEntry(VisitsDTO visitsDTO) {
        visitsDAO.visitsEntry(visitsDTO);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @Transactional
    public Map<String, String> visitsDelete(Map<String, Integer> map) {
        visitsDAO.visitsDelete(map);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }

    @Transactional(readOnly = true)
    public VisitsDTO selectVisits(int visitsId) {
        return visitsDAO.selectVisits(visitsId);
    }

    @Transactional(readOnly = true)
    public List<VisitsDTO> selectVisitsAll() {
        return visitsDAO.selectVisitsAll();
    }


}
