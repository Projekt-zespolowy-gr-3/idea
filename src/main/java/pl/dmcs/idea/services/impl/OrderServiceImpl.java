package pl.dmcs.idea.services.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pl.dmcs.idea.dto.CartFurnitureDTO;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.dto.mappers.FurnitureMapper;
import pl.dmcs.idea.dto.mappers.OrderMapper;
import pl.dmcs.idea.entities.Client;
import pl.dmcs.idea.entities.Furniture;
import pl.dmcs.idea.entities.Order;
import pl.dmcs.idea.entities.OrderFurniture;
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

            order.setClient((Client) accessLevelRepository.findFirstByAccountLogin(orderDTO.getUsername()).orElseThrow(() -> new AppBaseException("user.not.found.error")));
                for(CartFurnitureDTO cartFurnitureDTO : orderDTO.getFurnitures()){
                    Furniture furniture = furnitureRepository.findByBusinessKeyAndAmountGreaterThanEqual(cartFurnitureDTO.getId(), cartFurnitureDTO.getQuantity()).orElseThrow(() -> new AppBaseException("furniture.stock.insufficient"));
                    furniture.setAmount(furniture.getAmount() - cartFurnitureDTO.getQuantity());
                    OrderFurniture orderFurniture = new OrderFurniture();
                    orderFurniture.setOrder(order);
                    orderFurniture.setFurniture(furniture);
                    orderFurniture.setQuantity(cartFurnitureDTO.getQuantity());
                    order.getOrderFurnitureList().add(orderFurniture);
                    furnitureRepository.save(furniture);
                }
                orderRepository.saveAndFlush(order);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public List<OrderDTO> getOrders() throws AppBaseException {
        try {
            List<Order> orders = orderRepository.findAll().stream().toList();
            return getOrderDtos(orders);
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
            List<Order> orders = orderRepository.findAllByClientAccountLogin(username).stream().toList();
            return getOrderDtos(orders);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    List<OrderDTO> getOrderDtos(List<Order> orders){
            List<OrderDTO> orderDtos = new ArrayList<>();
            for(Order order : orders){
                OrderDTO orderDTO = OrderMapper.mapToDto(order);
                for(OrderFurniture orderFurniture : order.getOrderFurnitureList()){
                    FurnitureDTO furnitureDTO = FurnitureMapper.mapToDto(orderFurniture.getFurniture());
                    furnitureDTO.setCartQuantity(orderFurniture.getQuantity());
                    orderDTO.getFurnitureObjects().add(furnitureDTO);
                }
                orderDtos.add(orderDTO);
            }
            return orderDtos;
    }
}
