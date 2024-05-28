package com.server.boot.service;

import com.server.boot.dao.InboundDAO;
import com.server.boot.dao.OutboundDAO;
import com.server.boot.dao.StockDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OutboundService {

    private final OutboundDAO outboundDAO;
    private final StockDAO stockDAO;
    private final InboundDAO inboundDAO;

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutOrder(Map<String, String> map) {
        return outboundDAO.selectOutOrder(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectUploadFile(Map<String, String> map) {
        return outboundDAO.selectUploadFile(map);
    }

    @Transactional
    @CacheEvict(cacheNames = "outOrderPrint", allEntries = true)
    public void insertUploadFile(List<Map<String, String>> list) {

        // 작업번호 체번
        String transNo = outboundDAO.selectTransNo();
        int lineNo = 1;

        // 입고 예정테이블 업로드
        for(Map map : list) {
            map.put("LINE_NO", lineNo);
            map.put("TRANS_NO", transNo);
            outboundDAO.insertUploadFile(map);
            lineNo++;
        }

    }

    @Transactional
    public Map<String, String> outGenerateU(Map<String, String> map) {
        Map<String, String> resultMap = new HashMap<>();

        outboundDAO.outGenerateU(map);
        resultMap.put("result", "success");
        return resultMap;
    }

    @Transactional(readOnly = true)
    public String outGenerateMDValid(List<Map<String, String>> list) {
        Map<String, String> blankMap = new HashMap<>();

        List<Map<String, String>> stockData = stockDAO.selectStock(blankMap);

        boolean itemFlag = false;

        for(Map map : list) {

            List<Map<String, String>> uploadData = outboundDAO.selectOutOrderEntryDetail(map);

            for(Map item1 : uploadData) {

                for(Map item2 : stockData) {
                    int item1OrderQty = Integer.parseInt((String)item1.get("ORDER_QTY"));
                    int item2OrderQty = Integer.parseInt((String)item2.get("ORDER_QTY"));

                    if(Objects.equals(item1.get("ITEM_CD"), item2.get("ITEM_CD")) && item1OrderQty <= item2OrderQty) {
                        itemFlag = true;
                    }
                }

                if(itemFlag == false) {
                    return "fail";
                }
            }
        }

        return "success";
    }

    @Transactional
    public void outGenerateMD(List<Map<String, String>> list) {

        for(Map map : list) {
            List<Map<String, String>> result = outboundDAO.selectOutOrder(map);

            for(Map item : result) {
                item.put("TRANS_NO", item.get("ORDER_NO_CUST"));
                item.put("INSPECTION_YN", "N");
                outboundDAO.outGenerateD(item);
            }

            outboundDAO.outGenerateM(result.get(0));
            outboundDAO.outGenerateD2(result.get(0));
        }
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutOrderEntry(Map<String, String> map) {
        return outboundDAO.selectOutOrderEntry(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutOrderEntryDetail(Map<String, String> map) {
        return outboundDAO.selectOutOrderEntryDetail(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutInspection(Map<String, String> map) {
        return outboundDAO.selectOutInspection(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutInspectionDetail(Map<String, String> map) {
        return outboundDAO.selectOutInspectionDetail(map);
    }

    @Transactional(readOnly = true)
    public List<Map<String, String>> selectOutConfirmValid(Map<String, String> map) {
        return outboundDAO.selectOutConfirmValid(map);
    }

    @Transactional
    public void outInspection(List<Map<String, String>> list) {

        for(Map map : list) {
            outboundDAO.outInspection(map);
        }
    }

    @Transactional
    public void outInspectionCancel(List<Map<String, String>> list) {

        for(Map map : list) {
            outboundDAO.outInspectionCancel(map);
        }
    }

    @Transactional
    @CacheEvict(cacheNames = {"stock", "stockPrint"}, allEntries = true)
    public void outConfirm(Map<String, String> map) {

        Map<String, String> blankMap = new HashMap<>();

        List<Map<String, String>> stockData = stockDAO.selectStock(blankMap);

        List<Map<String,String>> list = outboundDAO.selectOutInspectionDetail(map);


        for(Map item : list) {
            for(Map item2 : stockData) {

                if(Objects.equals(item.get("ITEM_CD"), item2.get("ITEM_CD"))) {
                    int itemOrderQty = Integer.parseInt((String)item.get("ORDER_QTY"));
                    int item2OrderQty = Integer.parseInt((String)item2.get("ORDER_QTY"));

                    int sumStockItem = item2OrderQty - itemOrderQty;

                    if(sumStockItem == 0) {
                        outboundDAO.deleteStock((String)item.get("ITEM_CD"));
                    }

                    else {

                        String convertStringOrderQty = String.valueOf(sumStockItem);

                        Map<String, String> insertMap = new HashMap<>();

                        insertMap.put("ITEM_CD", (String)item.get("ITEM_CD"));
                        insertMap.put("ORDER_QTY", convertStringOrderQty);

                        inboundDAO.updateStock(insertMap);
                    }
                }
            }
        }

    }

}
