package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.CategoryService;
import by.teachmeskills.eshop.services.ProductService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.SEARCH_PARAM;

@Tag(name = "search", description = "The search API")
@RestController
@RequestMapping("/search")
public class SearchController {
    private final CategoryService categoryService;
    private final ProductService productService;

    public SearchController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @Operation(
            summary = "Info for search filter",
            description = "Info for search filter",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Info has been found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Could not find info"
            )
    })
    @GetMapping
    public ResponseEntity openSearchPage() {
        return new ResponseEntity<>(categoryService.getSearchPageData(), HttpStatus.OK);
    }


    @Operation(
            summary = "Search result",
            description = "Search result",
            tags = {"search"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Info has been found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Could not find info"
            )
    })
    @PostMapping
    public ResponseEntity getSearchResult(@RequestParam(SEARCH_PARAM) String searchParametr) {
        List<ProductDto> productDtos = productService.getSearchResult(searchParametr);
        if (Optional.ofNullable(productDtos).isPresent()) {
            return new ResponseEntity<>(productDtos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}