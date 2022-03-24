package pl.dmcs.idea.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.dto.mappers.OrderMapper;
import pl.dmcs.idea.entities.Client;
import pl.dmcs.idea.entities.Furniture;
import pl.dmcs.idea.entities.Order;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.repositories.AccessLevelRepository;
import pl.dmcs.idea.repositories.FurnitureRepository;
import pl.dmcs.idea.repositories.OrderRepository;
import pl.dmcs.idea.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final FurnitureRepository furnitureRepository;
    private final AccessLevelRepository accessLevelRepository;

    @Override
    public void addOrder(OrderDTO orderDTO) throws AppBaseException {
        try {
            Order order = new Order();
            order.setBusinessKey(UUID.randomUUID().toString());

            order.setClient((Client) accessLevelRepository.findByAccountLogin(orderDTO.getUsername()).orElseThrow(AppBaseException::new));

            List<Furniture> furnitures = new ArrayList<>();
            for(String key : orderDTO.getFurnitures().stream().map(FurnitureDTO::getBusinessKey).toList())
                furnitures.add(furnitureRepository.findByBusinessKey(key).orElseThrow(AppBaseException::new));
            order.setFurnitures(furnitures);

            orderRepository.saveAndFlush(order);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public List<OrderDTO> getOrders() throws AppBaseException {
        try {
            return orderRepository.findAll().stream().map(OrderMapper::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public OrderDTO getOrder(String key) throws AppBaseException {
        try {
            return OrderMapper.mapToDto(orderRepository.findByBusinessKey(key)
                    .orElseThrow(() -> new AppBaseException("order.not.found")));
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public List<OrderDTO> getOrdersForClient(String username) throws AppBaseException {
        try {
            return orderRepository.findAllByClientAccountLogin(username).stream().map(OrderMapper::mapToDto).toList();
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }
}
