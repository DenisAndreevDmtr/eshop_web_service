package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.ProductDto;
import by.teachmeskills.eshop.services.ProductService;
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

@Tag(name = "product", description = "The product API")
@RestController
@Validated
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Open product information",
            description = "Open product information",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product has been found",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product has not been found - not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity openProductPage(@Min(value = 1) @PathVariable int id) {
        ProductDto productDto = productService.getProductData(id);
        if (Optional.ofNullable(productDto).isPresent()) {
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(
            summary = "Create product",
            description = "Create product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product has been created",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product has not been created - bad request"
            )
    })
    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        ProductDto createdProductDto = productService.createFromDto(productDto);
        if (Optional.ofNullable(createdProductDto).isPresent()) {
            return new ResponseEntity<>(productDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Update product",
            description = "Update product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product has been updated",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product has not been updated - bad request"
            )
    })
    @PostMapping("/update")
    public ResponseEntity updateProduct(@RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.updateFromDto(productDto);
        if (Optional.ofNullable(updatedProductDto).isPresent()) {
            return new ResponseEntity<>(productDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Operation(
            summary = "Delete product",
            description = "Delete product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product has been deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product has not been deleted - bad request"
            )
    })
    @PostMapping("/delete")
    public ResponseEntity deleteProduct(@RequestBody ProductDto productDto) {
        try {
            productService.deleteFromDto(productDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}