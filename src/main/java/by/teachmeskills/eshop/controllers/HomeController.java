package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "home", description = "The home API")
@RestController
@RequestMapping("/home")
public class HomeController {
    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Operation(
            summary = "Open home page information",
            description = "Open home page information",
            tags = {"home"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find home page info",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found - not found"
            )
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getHomePage() {
        try {
            return new ResponseEntity<>(categoryService.getHomePageData(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}