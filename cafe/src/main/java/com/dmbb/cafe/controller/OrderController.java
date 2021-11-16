package com.dmbb.cafe.controller;

import com.dmbb.cafe.model.dto.OrderDTO;
import com.dmbb.cafe.service.OrderManagerService;
import com.dmbb.cafe.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderManagerService orderManagerService;

    @PostMapping
    public String putOrder(@RequestBody OrderDTO orderDTO) {
        return orderManagerService.putOrder(orderDTO);
    }

    @GetMapping("/status/{orderId}")
    public String getOrderStatus(@PathVariable String orderId) {
        return "ok";
    }

}
