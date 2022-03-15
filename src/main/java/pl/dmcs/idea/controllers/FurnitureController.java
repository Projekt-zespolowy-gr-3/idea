package pl.dmcs.idea.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.idea.dto.FurnitureDTO;
import pl.dmcs.idea.entities.Category;
import pl.dmcs.idea.exceptions.AppBaseException;
import pl.dmcs.idea.services.FurnitureService;

import java.util.Set;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("furnitures")
public class FurnitureController {

    private final FurnitureService furnitureService;

    @GetMapping
    public ResponseEntity<?> getFurnitures() {
        try {
            return ResponseEntity.ok().body(furnitureService.getFurnitures());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @GetMapping(params = {"page", "size"})
    public ResponseEntity<?> getFurnituresPagination(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "5") Integer size) {
        try {
            return ResponseEntity.ok().body(furnitureService.getFurnitersPagination(page, size));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @GetMapping("/key")
    public ResponseEntity<?> getFurniture(String businessKey) {
        try {
            return ResponseEntity.ok().body(furnitureService.getFurniture(businessKey));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @PostMapping("/furniture")
    public ResponseEntity<?> addFurniture(@RequestBody FurnitureDTO furnitureDTO) {
        try {
            furnitureService.addFurniture(furnitureDTO);
            return ResponseEntity.ok("furniture.add.success");
        } catch (AppBaseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("unexpected.error");
        }
    }

    @GetMapping("/categories")
    public Set<String> getCategories() {
        return Category.labels();
    }
}
