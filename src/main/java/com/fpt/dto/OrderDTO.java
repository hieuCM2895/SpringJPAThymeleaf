package com.fpt.dto;

import com.fpt.model.OrderItems;
import com.fpt.model.User;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class OrderDTO {

    private User user;

    private Set<OrderItems> listOfOrderItems;

    private Date orderDate;

    private int orderStatus;

    private Date requiredDate;

    private Date shippedDate;
}
