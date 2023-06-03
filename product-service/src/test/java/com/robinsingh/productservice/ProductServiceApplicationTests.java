package com.robinsingh.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robinsingh.productservice.dto.ProductRequest;
import com.robinsingh.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    public static void setProperty(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {

        ProductRequest productRequest =  getProductRequest();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldGetProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("iPhone13")
                .description("iPhone")
                .price(BigDecimal.valueOf(1300))
                .build();
    }

}
