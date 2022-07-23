package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@Tag(name = "category", description = "The category API")
@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Find category details",
            description = "Find category details",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category has been found",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Category has not been found - not found"
            )
    })
    @GetMapping("/{id}/{page}")
    public ResponseEntity<CategoryDto> openCategoryPagePaging(@Min(value = 1) @PathVariable int id, @Min(value = 1) @PathVariable int page) {
        CategoryDto categoryDto = categoryService.getCategoryDataPaging(id, page);
        if (Optional.ofNullable(categoryDto).isPresent()) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(
            summary = "Create category",
            description = "Create category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category has been created",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category has not been created - bad request"
            )
    })
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategoryDto = categoryService.createCategoryFromDto(categoryDto);
        if (Optional.ofNullable(createdCategoryDto).isPresent()) {
            return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Operation(
            summary = "Update category",
            description = "Update category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category has been updated",
                    content = @Content(schema = @Schema(implementation = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category has not been updated - bad request"
            )
    })
    @PostMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategoryDto = categoryService.updateCategoryFromDto(categoryDto);
        if (Optional.ofNullable(updatedCategoryDto).isPresent()) {
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Operation(
            summary = "Delete category",
            description = "Delete category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category has been deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category has not been deleted - bad request"
            )
    })
    @PostMapping("/delete")
    public ResponseEntity<CategoryDto> deleteCategory(@RequestBody CategoryDto categoryDto) {
        try {
            categoryService.deleteCategoryFromDto(categoryDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}