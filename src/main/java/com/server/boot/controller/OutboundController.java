package com.server.boot.controller;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.InboundService;
import com.server.boot.service.OutboundService;
import com.server.boot.service.UserService;
import com.server.boot.socket.MyWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://64.176.225.239:8080")
public class OutboundController {

    private final OutboundService outboundService;

    @PostMapping("/selectOutOrder")
    public List<Map<String, String>> selectOutOrder(@RequestBody Map<String, String> map, @SessionAttribute("user") UserDTO user) {
        return outboundService.selectOutOrder(map);
    }

    @PostMapping("/selectUploadFileO")
    public List<Map<String, String>> selectUploadFile(@RequestBody Map<String, String> map) {
        return outboundService.selectUploadFile(map);
    }

    @PostMapping("/insertUploadFileO")
    public void insertUploadFile(@RequestBody List<Map<String, String>> list) {
        outboundService.insertUploadFile(list);
    }

    @PostMapping("/outGenerateU")
    public Map<String, String> outGenerateU(@RequestBody Map<String, String> map) {
        return outboundService.outGenerateU(map);
    }

    @PostMapping("/selectOutOrderEntry")
    public List<Map<String, String>> selectOutOrderEntry(@RequestBody Map<String, String> map) {
        return outboundService.selectOutOrderEntry(map);
    }

    @PostMapping("/selectOutOrderEntryDetail")
    public List<Map<String, String>> selectOutOrderEntryDetail(@RequestBody Map<String, String> map) {
        return outboundService.selectOutOrderEntryDetail(map);
    }

    @PostMapping("/outGenerateMDValid")
    public String outGenerateMDValid(@RequestBody List<Map<String, String>> list) {
        return outboundService.outGenerateMDValid(list);
    }

    @PostMapping("/outGenerateMD")
    public void outGenerateMD(@RequestBody List<Map<String, String>> list) {
        outboundService.outGenerateMD(list);
    }

    @PostMapping("/selectOutInspection")
    public List<Map<String, String>> selectOutInspection(@RequestBody Map<String, String> map) {
        return outboundService.selectOutInspection(map);
    }

    @PostMapping("/selectOutInspectionDetail")
    public List<Map<String, String>> selectOutInspectionDetail(@RequestBody Map<String, String> map) {
        return outboundService.selectOutInspectionDetail(map);
    }

    @PostMapping("/outInspection")
    public void outInspection(@RequestBody List<Map<String, String>> list) {
        outboundService.outInspection(list);
    }

    @PostMapping("/outInspectionCancel")
    public void outInspectionCancel(@RequestBody List<Map<String, String>> list) {
        outboundService.outInspectionCancel(list);
    }

    @PostMapping("/outConfirm")
    public void outConfirm(@RequestBody Map<String, String> map) {
        outboundService.outConfirm(map);
    }

    @PostMapping("/selectOutConfirmValid")
    public List<Map<String, String>> selectOutConfirmValid(@RequestBody Map<String, String> map) {
        return outboundService.selectOutConfirmValid(map);
    }



}
