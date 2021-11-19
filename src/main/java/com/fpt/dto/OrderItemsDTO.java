package com.fpt.dto;

import com.fpt.model.Order;
import com.fpt.model.Product;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrderItemsDTO {

    private Product product;

    private Order order;

    private int quantity;

    private double price;

    private String discount;
}
