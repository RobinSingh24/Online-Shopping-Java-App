package com.robinsingh.inventoryservice;

import com.robinsingh.inventoryservice.model.Inventory;
import com.robinsingh.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = Inventory.builder()
					.skuCode("iPhone 13")
					.quantity(100)
					.build();
			Inventory inventory1 = Inventory.builder()
					.skuCode("iPad 8")
					.quantity(200)
					.build();
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}
