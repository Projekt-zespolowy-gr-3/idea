package pl.dmcs.idea.dto.mappers;

import pl.dmcs.idea.dto.OrderDTO;
import pl.dmcs.idea.entities.Order;
import pl.dmcs.idea.utils.DateFormatter;


public class OrderMapper {

    public static OrderDTO mapToDto(Order order) {
        return OrderDTO.builder()
                .businessKey(order.getBusinessKey())
                .username(order.getClient().getAccount().getLogin())
//                .furnitureObjects(order.getOrderFurnitureList().stream().map(f -> FurnitureMapper.mapToDto(f.getFurniture())).toList())
                .date(DateFormatter.dateToString(order.getDate()))
                .build();
    }
}
