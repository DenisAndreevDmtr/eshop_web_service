package by.teachmeskills.eshop.utils;

public enum RequestParamsEnum {
    LOGIN("username"),
    PASSWORD("password"),
    CONFIRM_PASSWORD("confirmpassword"),
    NAME("name"),
    SURNAME("surname"),
    DATE_BORN("dateborn"),
    EMAIL("email"),
    COMMAND("command"),
    SHOPPING_CART("cart"),
    CATEGORY("category"),
    PRODUCT("product"),
    SHOPPING_CART_PRODUCTS("cartProductsList"),
    PRODUCT_ID("product_id"),
    CATEGORIES("categories"),
    LOGGED_IN_USER("loggedInUser"),
    CATEGORY_ID("category_id"),
    ID_ORDER("idOrder"),
    PRICE_ORDER("priceOrder"),
    USER_ORDERS("userOrders"),
    SEARCH_PARAMETR("searchParametr"),
    SEARCH_RESULT("search_result"),
    NAME_CATEGORY("namecategory"),
    ERROR_PARAM("error"),
    NUMBER_OF_PAGES("number_of_pages");

    private final String value;

    RequestParamsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}