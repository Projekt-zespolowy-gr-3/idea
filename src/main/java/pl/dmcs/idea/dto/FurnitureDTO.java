package pl.dmcs.idea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FurnitureDTO {

    private String businessKey;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String photo;
    private int amount;
}
