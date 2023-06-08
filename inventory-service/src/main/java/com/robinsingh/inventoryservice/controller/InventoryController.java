package com.robinsingh.inventoryservice.controller;

import com.robinsingh.inventoryservice.dto.InventoryResponse;
import com.robinsingh.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode ){
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return inventoryService.findBySkuCode(skuCode);
    }
}
