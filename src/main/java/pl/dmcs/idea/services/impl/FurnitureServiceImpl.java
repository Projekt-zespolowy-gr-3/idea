package pl.dmcs.idea.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.dto.PaginationFurnitureDTO;
import pl.dmcs.idea.dto.mappers.FurnitureMapper;
import pl.dmcs.idea.entities.Furniture;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.repositories.FurnitureRepository;
import pl.dmcs.idea.services.FurnitureService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FurnitureServiceImpl implements FurnitureService {

    private final FurnitureRepository furnitureRepository;

    @Override
    public PaginationFurnitureDTO getFurnitersPagination(Integer page, Integer pageSize) throws AppBaseException {
        try {
            Pageable paging = PageRequest.of(page, pageSize);
            PaginationFurnitureDTO paginationFurnitureDTO = new PaginationFurnitureDTO();
            paginationFurnitureDTO.setFurnitureList(furnitureRepository.findAll(paging).stream().map(FurnitureMapper::mapToDto).collect(Collectors.toList()));
            paginationFurnitureDTO.setTotalRows(furnitureRepository.findAll(paging).getTotalElements());
            return paginationFurnitureDTO;
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public void addFurniture(FurnitureDTO furnitureDTO) throws AppBaseException {
        try {
            Furniture furniture = FurnitureMapper.mapFromDto(furnitureDTO);
            furniture.setBusinessKey(UUID.randomUUID().toString());
            furnitureRepository.saveAndFlush(furniture);
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public List<FurnitureDTO> getFurnitures() throws AppBaseException {
        try {
            return furnitureRepository.findAll().stream().map(FurnitureMapper::mapToDto).collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new AppBaseException("unexpected.error");
        }
    }

    @Override
    public FurnitureDTO getFurniture(String businessKey) throws AppBaseException {
        return FurnitureMapper.mapToDto(furnitureRepository.findByBusinessKey(businessKey)
                .orElseThrow(() -> new AppBaseException("furniture.not.found")));
    }
}
