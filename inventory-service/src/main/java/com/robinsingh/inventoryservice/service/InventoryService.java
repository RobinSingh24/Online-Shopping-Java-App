package com.robinsingh.inventoryservice.service;

import com.robinsingh.inventoryservice.model.Inventory;
import com.robinsingh.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean findBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
