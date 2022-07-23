package by.teachmeskills.eshop.utils;

public enum PagesPathEnum {
    HOME_PAGE("home"),
    SIGN_IN_PAGE("signin"),
    CATEGORY_PAGE("category"),
    CART_PAGE("cart"),
    PRODUCT_PAGE("product"),
    REGISTER_PAGE("register"),
    STATUS_REGISTER_PAGE("statusregister"),
    PROFILE_PAGE("profile"),
    ORDER_PAGE("order"),
    STATUS_FAIL_PAGE("statusfail"),
    SEARCH_PRODUCT_PAGE("searchproduct"),
    ERROR_PAGE("error");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}