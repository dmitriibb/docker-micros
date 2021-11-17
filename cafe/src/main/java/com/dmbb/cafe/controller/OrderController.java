package com.dmbb.cafe.controller;

import com.dmbb.cafe.model.OrderStatus;
import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.model.dto.OrderResultDTO;
import com.dmbb.cafe.model.dto.OrderStatusDTO;
import com.dmbb.cafe.service.OrderManagerService;
import com.dmbb.cafe.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderManagerService orderManagerService;
    private final OrderStatusService orderStatusService;

    @PostMapping
    public String putOrder(@RequestBody OrderDTO orderDTO) {
        return orderManagerService.putOrder(orderDTO);
    }

    @GetMapping("/status/{orderId}")
    public OrderStatusDTO getOrderStatus(@PathVariable String orderId) {
        return orderStatusService.getOrderStatusById(orderId);
    }

    @GetMapping("/status/raw/{orderId}")
    public OrderStatus getOrderStatusRaw(@PathVariable String orderId) {
        return orderStatusService.getRawOrderStatus(orderId);
    }

    @GetMapping("/get/{orderId}")
    public OrderResultDTO getOrder(@PathVariable String orderId) {
        return orderStatusService.getOrderResult(orderId);
    }

}
