package pl.dmcs.idea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dmcs.idea.entities.Furniture;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartFurnitureDTO {

    private String id;
    private String name;
    private BigDecimal price;
    private int quantity;

}
