package com.robinsingh.inventoryservice.service;

import com.robinsingh.inventoryservice.dto.InventoryResponse;
import com.robinsingh.inventoryservice.model.Inventory;
import com.robinsingh.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> findBySkuCode(List<String> skuCode) {
        log.info("wait started");
        Thread.sleep(10000);
        log.info("wait ended");
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build())
                .toList();

    }
}
