package pl.dmcs.idea.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String businessKey;
    private List<CartFurnitureDTO> furnitures;
    private List<FurnitureDTO> furnitureObjects = new ArrayList<>();
    private String username;
    private String date;
    private Double totalPrice;
}
