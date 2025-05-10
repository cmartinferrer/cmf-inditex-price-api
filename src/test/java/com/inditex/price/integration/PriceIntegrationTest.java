package com.inditex.price.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PriceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test 1: Request at 10:00 on June 14 for product 35455 and brand 1")
    void whenRequestAt10On14_thenReturnPriceList1() throws Exception {
        performRequestAndValidate("2020-06-14T10:00:00", 1, 35.50);
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on June 14 for product 35455 and brand 1")
    void whenRequestAt16On14_thenReturnPriceList2() throws Exception {
        performRequestAndValidate("2020-06-14T16:00:00", 2, 25.45);
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on June 14 for product 35455 and brand 1")
    void whenRequestAt21On14_thenReturnPriceList1() throws Exception {
        performRequestAndValidate("2020-06-14T21:00:00", 1, 35.50);
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on June 15 for product 35455 and brand 1")
    void whenRequestAt10On15_thenReturnPriceList3() throws Exception {
        performRequestAndValidate("2020-06-15T10:00:00", 3, 30.50);
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on June 16 for product 35455 and brand 1")
    void whenRequestAt21On16_thenReturnPriceList4() throws Exception {
        performRequestAndValidate("2020-06-16T21:00:00", 4, 38.95);
    }

    private void performRequestAndValidate(String dateTime, int expectedPriceList, double expectedPrice) throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", dateTime)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(expectedPriceList))
                .andExpect(jsonPath("$.price").value(expectedPrice));
    }
}
