package pl.dmcs.idea.dto.mappers;

import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.entities.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO mapToDto(Order order) {
        return OrderDTO.builder()
                .businessKey(order.getBusinessKey())
                .username(order.getClient().getAccount().getLogin())
                .furnitures(order.getFurnitures().stream().map(FurnitureMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }
}
