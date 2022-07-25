package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CartDto;
import by.teachmeskills.eshop.dto.OrderDto;
import by.teachmeskills.eshop.services.impl.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCT_ID_PARAM;
import static by.teachmeskills.eshop.utils.EshopConstants.QANTITY_PARAM;

@Tag(name = "cart", description = "The cart API")
@RestController
@Validated
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @Operation(
            summary = "Add quantity of products to the cart",
            description = "Add quantity of to the products",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products have been added to the cart",
                    content = @Content(schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products have not been added to the cart- Bad request"
            )
    })
    @PostMapping("/add")
    public ResponseEntity<CartDto> addProductToCart(@Min(value = 1) @RequestParam(PRODUCT_ID_PARAM) String id, @Min(value = 1) @RequestParam(QANTITY_PARAM) String quantity, @RequestBody CartDto cartDto) {
        int productId = Integer.parseInt(id);
        int productQuantity = Integer.parseInt(quantity);
        CartDto cartDtoResponse = cartService.addProductToCart(productId, productQuantity, cartDto);
        if (Optional.ofNullable(cartDtoResponse).isPresent()) {
            return new ResponseEntity<>(cartDtoResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Remove quantity of products from the cart",
            description = "Remove quantity of products from the cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products have been removed from the cart",
                    content = @Content(schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products have not been removed from the cart- Bad request"
            )
    })
    @PostMapping("/remove")
    public ResponseEntity<CartDto> removeProductFromCart(@Min(value = 1) @RequestParam(PRODUCT_ID_PARAM) String id, @Min(value = 1) @RequestParam(QANTITY_PARAM) String quantity, @RequestBody CartDto cartDto) {
        int productId = Integer.parseInt(id);
        int productQuantity = Integer.parseInt(quantity);
        CartDto cartDtoResponse = cartService.removeProductFromCart(productId, productQuantity, cartDto);
        if (Optional.ofNullable(cartDtoResponse).isPresent()) {
            return new ResponseEntity<>(cartDtoResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "Purchase products, create order",
            description = "Purchase products, create order",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products have been purchased, order has been created",
                    content = @Content(schema = @Schema(implementation = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Products have not been purchased, order has not been created - Bad request"
            )
    })
    @PostMapping("/purchase")
    public ResponseEntity<OrderDto> purchase(@RequestBody CartDto cartDto) {
        OrderDto orderDto = cartService.checkOut(cartDto);
        if (Optional.ofNullable(orderDto).isPresent()) {
            return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}