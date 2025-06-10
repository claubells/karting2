package com.example.report_service.clients;

import com.example.report_service.dto.ReceiptDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "report-service",
        path = "/api/receipt",
        contextId = "reportToReceipt",
        configuration = {FeignClient.class})
public interface ReceiptFeignClient {

    @GetMapping("/minimal")
    List<ReceiptDTO> getMinimalReceipts();

    @GetMapping("/reports/turns")
    Integer getIngresoPorVueltasYMese(@RequestParam int turns, @RequestParam String month);

    @GetMapping("/reports/people")
    Integer getIngresoPorGrupoYMese(@RequestParam int min, @RequestParam int max, @RequestParam String month);

}
