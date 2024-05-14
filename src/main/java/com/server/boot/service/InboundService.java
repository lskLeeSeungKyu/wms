package com.server.boot.service;

import com.server.boot.dao.InboundDAO;
import com.server.boot.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InboundService {

    private final InboundDAO inboundDAO;

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbOrder(Map<String, String> map) {
        return inboundDAO.selectInbOrder(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectUploadFile(Map<String, String> map) {
        return inboundDAO.selectUploadFile(map);
    }

    @Transactional
    public void insertUploadFile(List<Map<String, String>> list) {

        // 작업번호 체번
        String transNo = inboundDAO.selectTransNo();
        int lineNo = 1;

        // 입고 예정테이블 업로드
        for(Map map : list) {
            map.put("LINE_NO", lineNo);
            map.put("TRANS_NO", transNo);
            inboundDAO.insertUploadFile(map);
            lineNo++;
        }

    }

    @Transactional
    public Map<String, String> inbGenerateU(Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<>();

        inboundDAO.inbGenerateU(map);
        resultMap.put("result", "success");
        return resultMap;
    }

    @Transactional
    public void inbGenerateMD(List<Map<String, String>> list) {

        for(Map map : list) {
            List<Map<String, String>> result = inboundDAO.selectInbOrder(map);

            for(Map item : result) {
                item.put("TRANS_NO", item.get("ORDER_NO_CUST"));
                item.put("INSPECTION_YN", "N");
                inboundDAO.inbGenerateD(item);
            }

            inboundDAO.inbGenerateM(result.get(0));
            inboundDAO.inbGenerateD2(result.get(0));
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbOrderEntry(Map<String, String> map) {
        return inboundDAO.selectInbOrderEntry(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbOrderEntryDetail(Map<String, String> map) {
        return inboundDAO.selectInbOrderEntryDetail(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbInspection(Map<String, String> map) {
        return inboundDAO.selectInbInspection(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbInspectionDetail(Map<String, String> map) {
        return inboundDAO.selectInbInspectionDetail(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectInbConfirmValid(Map<String, String> map) {
        return inboundDAO.selectInbConfirmValid(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectStock(Map<String, String> map) {
        return inboundDAO.selectStock(map);
    }

    @Transactional
    public void inbInspection(List<Map<String, String>> list) {

        for(Map map : list) {
            inboundDAO.inbInspection(map);
        }
    }

    @Transactional
    public void inbInspectionCancel(List<Map<String, String>> list) {

        for(Map map : list) {
            inboundDAO.inbInspectionCancel(map);
        }
    }

    @Transactional
    public void inbConfirm(Map<String, String> map) {

        inboundDAO.inbConfirm(map);
        List<Map<String,String>> list = inboundDAO.selectInbInspectionDetail(map);

        for(Map item : list) {
            System.out.println("item = " + item);
            inboundDAO.insertStock(item);
        }
    }


}
