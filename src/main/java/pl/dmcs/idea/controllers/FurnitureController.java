package pl.dmcs.idea.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.dto.PaginationFurnitureDTO;
import pl.dmcs.idea.entities.Category;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.FurnitureService;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("furnitures")
public class FurnitureController {

    private final FurnitureService furnitureService;

    @GetMapping
    public List<FurnitureDTO> getFurnitures() throws AppBaseException {
        return furnitureService.getFurnitures();
    }

    @GetMapping(params = {"page", "size"})
    public PaginationFurnitureDTO getFurnituresPagination(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "5") Integer size) throws AppBaseException {
        return furnitureService.getFurnitersPagination(page, size);
    }

    @GetMapping("/{businessKey}")
    public FurnitureDTO getFurniture(@PathVariable String businessKey) throws AppBaseException {
        return furnitureService.getFurniture(businessKey);
    }

    @PostMapping("/furniture")
    public String addFurniture(@RequestBody FurnitureDTO furnitureDTO) throws AppBaseException {
        furnitureService.addFurniture(furnitureDTO);
        return "furniture.add.success";
    }

    @GetMapping("/categories")
    public Set<String> getCategories() {
        return Category.labels();
    }
}
