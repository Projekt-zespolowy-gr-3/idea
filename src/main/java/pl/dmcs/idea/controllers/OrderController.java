package pl.dmcs.idea.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.OrderService;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderDTO> getOrders() throws AppBaseException {
        return orderService.getOrders();
    }

    @PostMapping
    public String addOrder(@RequestBody OrderDTO orderDTO) throws AppBaseException {
        orderService.addOrder(orderDTO);
        return "order.add.success";
    }

    @GetMapping("/order/{key}")
    public OrderDTO getOrder(@PathVariable String key) throws AppBaseException {
        return orderService.getOrder(key);
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == authentication.principal.username")
    public List<OrderDTO> getOrdersForClient(@PathVariable String username) throws AppBaseException {
        return orderService.getOrdersForClient(username);
    }
}
