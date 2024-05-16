package com.server.boot.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InboundDAO {

    List<Map<String, String>> selectInbOrder(Map<String, String> map);

    List<Map<String, String>> selectUploadFile(Map<String, String> map);

    List<Map<String, String>> selectInbOrderEntry(Map<String, String> map);

    List<Map<String, String>> selectInbOrderEntryDetail(Map<String, String> map);

    List<Map<String, String>> selectInbInspection(Map<String, String> map);

    List<Map<String, String>> selectInbInspectionDetail(Map<String, String> map);

    List<Map<String, String>> selectInbConfirmValid(Map<String, String> map);


    void insertUploadFile(Map<String, String> map);

    String selectTransNo();

    void inbGenerateU(Map<String, String> map);

    void inbGenerateM(Map<String, String> map);

    void inbGenerateD(Map<String, String> map);

    void inbGenerateD2(Map<String, String> map);

    void inbInspection(Map<String, String> map);

    void inbInspectionCancel(Map<String, String> map);

    void inbConfirm(Map<String, String> map);

    void insertStock(Map<String, String> item);

}
