package com.robinsingh.inventoryservice.controller;

import com.robinsingh.inventoryservice.dto.InventoryResponse;
import com.robinsingh.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode ){
        return inventoryService.findBySkuCode(skuCode);
    }
}
