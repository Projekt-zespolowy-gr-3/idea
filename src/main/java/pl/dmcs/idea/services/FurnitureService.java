package pl.dmcs.idea.services;

import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.dto.PaginationFurnitureDTO;
import pl.dmcs.idea.exceptions.AppBaseException;

import java.util.List;

public interface FurnitureService {

    void addFurniture(FurnitureDTO furnitureDTO) throws AppBaseException;

    List<FurnitureDTO> getFurnitures() throws AppBaseException;

    PaginationFurnitureDTO getFurnitersPagination(String query, Integer page, Integer pageSize) throws AppBaseException;

    FurnitureDTO getFurniture(String businessKey) throws AppBaseException;
}
