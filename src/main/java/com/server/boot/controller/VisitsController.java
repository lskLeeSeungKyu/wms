package com.server.boot.controller;

import com.server.boot.dto.VisitsDTO;
import com.server.boot.service.VisitsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VisitsController {

    private final VisitsService visitsService;

    @PostMapping("/visitsEntry")
    public Map<String, String> visitsEntry(@RequestBody VisitsDTO visitsDTO) {
        return visitsService.visitsEntry(visitsDTO);
    }

    @PostMapping("/visitsDelete")
    public Map<String, String> visitsDelete(@RequestBody Map<String, Integer> map) {
        return visitsService.visitsDelete(map);
    }

    @GetMapping("/selectVisits")
    public VisitsDTO selectVisits(@RequestParam int visitsId) {
        VisitsDTO visitsDTO = visitsService.selectVisits(visitsId);
        return visitsDTO;
    }

    @GetMapping("/selectVisitsAll")
    public List<VisitsDTO> selectVisitsAll() {
        return visitsService.selectVisitsAll();
    }
}
