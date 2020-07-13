package com.example.orders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.orders.security.JwtAuthenticationController;
import com.example.orders.security.JwtAuthenticationEntryPoint;
import com.example.orders.security.JwtRequestFilter;
import com.example.orders.security.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PurchaseOrderService orderService;

    @MockBean
    private JwtAuthenticationController jwtAuthenticationController;

    @MockBean
    private JwtRequestFilter jwtRequestFilter;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private static ObjectMapper mapper = new ObjectMapper();

    @Disabled
    @Test
    public void testPostExample() throws Exception {

        OrderDetails orderDetails1 =
                new OrderDetails("Elsa Costume" + UUID.randomUUID().toString(), new BigDecimal(12));

        OrderDetails orderDetails2 = new OrderDetails("Anna Costume" + UUID.randomUUID().toString(),
                new BigDecimal(15));

        List<OrderDetails> orderDetailsList = List.of(orderDetails1, orderDetails2);

        PurchaseOrder order = new PurchaseOrder(new BigDecimal(1), orderDetailsList);
        Mockito.when(orderService.createOrder(ArgumentMatchers.any())).thenReturn(order);
        String json = mapper.writeValueAsString(order);

        mockMvc.perform(post("/ordrwereowriers").header("Authorization", "Bearer 5u43959").
                contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.orderAmount", Matchers.equalTo(1)));


    }
}
