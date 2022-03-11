package pl.dmcs.idea.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import pl.dmcs.idea.services.OrderService;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
}
