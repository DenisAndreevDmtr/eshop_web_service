package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.dto.CategoryDto;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.exceptions.AuthorizationException;
import by.teachmeskills.eshop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Tag(name = "authentication", description = "The authentication API")
@RestController
@Validated
@RequestMapping("/signin")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Login and open home page information",
            description = "Login and open home page information",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged in",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - unauthorized"
            )
    })
    @PostMapping
    public ResponseEntity<List<CategoryDto>> login(@RequestBody UserDto userDto) throws AuthorizationException {
        List<CategoryDto> categoryDtoList = userService.authenticate(userDto);
        if (Optional.ofNullable(categoryDtoList).isPresent()) {
            return new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    @Operation(
            summary = "Get user information and history of orders",
            description = "Get user information and history of orders",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Got user information",
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cant find user information"
            )
    })
    @GetMapping("/profile/{userId}/{pageNumber}")
    public ResponseEntity<UserDto> getUserData(
            @Min(value = 1)
            @PathVariable int userId,
            @Min(value = 1)
            @PathVariable int pageNumber) {
        UserDto userDto = userService.getDataAboutLoggedInUserPaging(userId, pageNumber);
        if (Optional.ofNullable(userDto).isPresent()) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}