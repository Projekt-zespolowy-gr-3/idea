package pl.dmcs.idea.services;

import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.exceptions.AppBaseException;

import java.util.List;

public interface OrderService {

    void addOrder(OrderDTO orderDTO) throws AppBaseException;
    List<OrderDTO> getOrders() throws AppBaseException;
    OrderDTO getOrder(String key) throws AppBaseException;
    List<OrderDTO> getOrdersForClient(String username) throws AppBaseException;
}
