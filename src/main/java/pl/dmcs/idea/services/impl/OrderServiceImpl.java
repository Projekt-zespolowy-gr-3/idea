package pl.dmcs.idea.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dmcs.idea.repositories.OrderRepository;
import pl.dmcs.idea.services.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
}
