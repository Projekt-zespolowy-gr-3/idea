package pl.dmcs.idea.dto.mappers;

import org.springframework.util.Base64Utils;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.entities.Furniture;

public class FurnitureMapper {

    public static FurnitureDTO mapToDto(Furniture furniture) {
        return FurnitureDTO.builder()
                .businessKey(furniture.getBusinessKey())
                .name(furniture.getName())
                .description(furniture.getDescription())
                .category(furniture.getCategory())
                .price(furniture.getPrice())
                .amount(furniture.getAmount())
                .photo(furniture.getPhoto() != null ? new String(Base64Utils.encode(furniture.getPhoto())) : "")
                .build();
    }

    public static Furniture mapFromDto(FurnitureDTO furnitureDTO) {
        return Furniture.builder()
                .name(furnitureDTO.getName())
                .description(furnitureDTO.getDescription())
                .category(furnitureDTO.getCategory())
                .price(furnitureDTO.getPrice())
                .amount(furnitureDTO.getAmount())
                .photo(furnitureDTO.getPhoto() != null ? Base64Utils.decode(furnitureDTO.getPhoto().getBytes()) : null)
                .build();
    }
}
