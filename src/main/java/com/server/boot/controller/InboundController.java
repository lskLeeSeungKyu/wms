package com.server.boot.controller;

import com.server.boot.dto.UserDTO;
import com.server.boot.service.InboundService;
import com.server.boot.service.UserService;
import com.server.boot.socket.MyWebSocketHandler;
import com.server.boot.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://64.176.225.239:8080")
public class InboundController {

    private final InboundService inboundService;

    @PostMapping("/selectInbOrder")
    public List<Map<String, String>> selectInbOrder(@RequestBody Map<String, String> map, @SessionAttribute("user") UserDTO user) {
        return inboundService.selectInbOrder(map);
    }

    @PostMapping("/selectUploadFile")
    public List<Map<String, String>> selectUploadFile(@RequestBody Map<String, String> map) {
        return inboundService.selectUploadFile(map);
    }

    @PostMapping("/insertUploadFile")
    public void insertUploadFile(@RequestBody List<Map<String, String>> list) {
        inboundService.insertUploadFile(list);
    }

    @PostMapping("/inbGenerateU")
    public Map<String, String> inbGenerateU(@RequestBody Map<String, String> map) {
        return inboundService.inbGenerateU(map);
    }

    @PostMapping("/selectInbOrderEntry")
    public List<Map<String, String>> selectInbOrderEntry(@RequestBody Map<String, String> map) {
        return inboundService.selectInbOrderEntry(map);
    }

    @PostMapping("/selectInbOrderEntryDetail")
    public List<Map<String, String>> selectInbOrderEntryDetail(@RequestBody Map<String, String> map) {
        return inboundService.selectInbOrderEntryDetail(map);
    }

    @PostMapping("/inbGenerateMD")
    public void inbGenerateMD(@RequestBody List<Map<String, String>> list) {
        inboundService.inbGenerateMD(list);
    }

    @PostMapping("/selectInbInspection")
    public List<Map<String, String>> selectInbInspection(@RequestBody Map<String, String> map) {
        return inboundService.selectInbInspection(map);
    }

    @PostMapping("/selectInbInspectionDetail")
    public List<Map<String, String>> selectInbInspectionDetail(@RequestBody Map<String, String> map) {
        return inboundService.selectInbInspectionDetail(map);
    }

    @PostMapping("/inbInspection")
    public void inbInspection(@RequestBody List<Map<String, String>> list) {
        inboundService.inbInspection(list);
    }

    @PostMapping("/inbInspectionCancel")
    public void inbInspectionCancel(@RequestBody List<Map<String, String>> list) {
        inboundService.inbInspectionCancel(list);
    }

    @PostMapping("/inbConfirm")
    public void inbConfirm(@RequestBody Map<String, String> map) {
        inboundService.inbConfirm(map);
    }

    @PostMapping("/selectInbConfirmValid")
    public List<Map<String, String>> selectInbConfirmValid(@RequestBody Map<String, String> map) {
        return inboundService.selectInbConfirmValid(map);
    }



}
