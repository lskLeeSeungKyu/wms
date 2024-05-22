package com.server.boot.controller;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import com.server.boot.dao.OutboundDAO;
import com.server.boot.service.InboundService;
import com.server.boot.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://10.101.52.96:8081")
public class JasperController {

    private final InboundService inboundService;
    private final StockService stockService;
    private final OutboundDAO outboundDAO;

    @GetMapping("/inbOrderPrint/{date}/{file}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> inbOrderPrint(@PathVariable("date") String date, @PathVariable("file") String file) throws Exception, JRException {

        Map<String, String> param = new HashMap<>();

        param.put("ORDER_DATE", date);
        param.put("FILE_NM", file);

        // jasper에 띄울 데이터 date에 맞춰 받아오기
        List<Map<String, String>> result = inboundService.selectInbOrder(param);

        // 받아온 데이터를 jasper datasource로 등록
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jasper/InboundOrder.jrxml");

        // jasper 컴파일할 양식 설정 - 만들어둔 jrxml 파일 경로 설정
        JasperReport compileReport = JasperCompileManager.compileReport(inputStream);

        // datasource를 매핑해 양식(jrxml)에 맞게 컴파일
        Map<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        // return 방식1. 컴파일된 pdf파일을 현재 폴더에 생성
//			JasperExportManager.exportReportToPdfFile(report, "BoardStatus.pdf");
//			return "generated";

        // return 방식2. 프린트 및 adobe pdf 화면 띄우기
        // *주의: 프론트에서 화면을 띄울 수 없고, 서버 url을 직접 띄워야함..
        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=InboundOrder.pdf");

        inputStream.close();

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    @GetMapping("/outOrderPrint/{date}/{file}")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> outOrderPrint(@PathVariable("date") String date, @PathVariable("file") String file) throws Exception, JRException {

        Map<String, String> param = new HashMap<>();

        param.put("ORDER_DATE", date);
        param.put("FILE_NM", file);

        List<Map<String, String>> result = outboundDAO.selectOutOrder(param);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jasper/OutboundOrder.jrxml");

        JasperReport compileReport = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=InboundOrder.pdf");

        inputStream.close();

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    @GetMapping("/stockPrint")
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> stockPrint() throws Exception, JRException {

        List<Map<String, String>> result = stockService.selectStockPrint();

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(result);

//        Resource resource = new ClassPathResource("jasper/Stock.jrxml");
//        InputStream inputStream = resource.getInputStream();
        //이렇게 하니까 NPE가 뜸. 왜 null인지는 모르겠는데 그래서 밑에처럼 함. 정상 작동.

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("jasper/Stock.jrxml");

        JasperReport compileReport = JasperCompileManager.compileReport(inputStream);

        Map<String, Object> map = new HashMap<>();
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Stock.pdf");

        //gc
        inputStream.close();

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }
}