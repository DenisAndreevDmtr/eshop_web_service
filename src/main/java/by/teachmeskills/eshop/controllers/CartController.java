package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.model.Cart;
import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.services.impl.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import static by.teachmeskills.eshop.utils.EshopConstants.PRODUCT_ID_PARAM;
import static by.teachmeskills.eshop.utils.EshopConstants.SHOPPING_CART;
import static by.teachmeskills.eshop.utils.EshopConstants.USER;
import static by.teachmeskills.eshop.utils.PagesPathEnum.CART_PAGE;

@RestController
@SessionAttributes({SHOPPING_CART})
@RequestMapping("/cart")
public class CartController {
    private static final String SHOPPING_CART = "cart";
    private final CartService cartService;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ModelAndView openCartPage() {
        return new ModelAndView(CART_PAGE.getPath());
    }


    @GetMapping("/add")
    public ModelAndView addProductToCart(@RequestParam(PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.addProductToCart(productId, shopCart);
    }

    @GetMapping("/remove")
    public ModelAndView removeProductFromCart(@RequestParam(PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.removeProductFromCart(productId, shopCart);
    }

    @GetMapping("/increase")
    public ModelAndView increaseProductAmount(@RequestParam(PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.increaseProductQuantity(productId, shopCart);
    }

    @GetMapping("/decrease")
    public ModelAndView decreaseProductAmount(@RequestParam(PRODUCT_ID_PARAM) String id, @ModelAttribute(SHOPPING_CART) Cart shopCart) {
        int productId = Integer.parseInt(id);
        return cartService.decreaseProductQuantity(productId, shopCart);
    }

    @GetMapping("/purchase")
    public ModelAndView purchase(@ModelAttribute(SHOPPING_CART) Cart shopCart, @SessionAttribute(USER) User user) {
        return cartService.checkOut(shopCart, user);
    }

    @ModelAttribute(SHOPPING_CART)
    public Cart shoppingCart() {
        return new Cart();
    }
}