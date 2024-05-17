package com.server.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OutboundDAO {

    List<Map<String, String>> selectOutOrder(Map<String, String> map);

    List<Map<String, String>> selectUploadFile(Map<String, String> map);

    List<Map<String, String>> selectOutOrderEntry(Map<String, String> map);

    List<Map<String, String>> selectOutOrderEntryDetail(Map<String, String> map);

    List<Map<String, String>> selectOutInspection(Map<String, String> map);

    List<Map<String, String>> selectOutInspectionDetail(Map<String, String> map);

    List<Map<String, String>> selectOutConfirmValid(Map<String, String> map);


    void insertUploadFile(Map<String, String> map);

    String selectTransNo();

    void outGenerateU(Map<String, String> map);

    void outGenerateM(Map<String, String> map);

    void outGenerateD(Map<String, String> map);

    void outGenerateD2(Map<String, String> map);

    void outInspection(Map<String, String> map);

    void outInspectionCancel(Map<String, String> map);

    void outConfirm(Map<String, String> map);

    void insertStock(Map<String, String> item);

    void deleteStock(String itemCd);

}
