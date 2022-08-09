package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.config.JwtProvider;
import by.teachmeskills.eshop.dto.AuthResponse;
import by.teachmeskills.eshop.dto.UserDto;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.exceptions.AuthorizationException;
import by.teachmeskills.eshop.services.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@Tag(name = "user", description = "The user API")
@RestController
@Validated
@RequestMapping("/user")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Operation(
            summary = "Login and get jwt token",
            description = "Login and get jwt token",
            tags = {"authentication"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully logged in system",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden - forbidden access"
            )
    })
    @PostMapping
    public AuthResponse login(@RequestBody UserDto userDto) throws AuthorizationException {
        User user = userService.findByLoginAndPassword(userDto.getLogin(), userDto.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
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
    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserData(
            @Min(value = 1)
            @PathVariable int userId,
            @Min(value = 0)
            @RequestParam(defaultValue = "0") int pageNumber,
            @Min(value = 1)
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        UserDto userDto = userService.getDataAboutLoggedInUser(userId, pageNumber, pageSize);
        if (Optional.ofNullable(userDto).isPresent()) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}