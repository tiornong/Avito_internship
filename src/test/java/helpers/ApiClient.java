package helpers;


import config.ApiConstants;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import model.Item;

import static io.restassured.RestAssured.given;

/**Класс для хранения методов, непосредственно взаимодействующих с API.*/
public class ApiClient {

    @Step("Клиент - создание товара")
    public ValidatableResponse createItem(Item item) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(item)
                .post(ApiConstants.ITEM_TEST_URL)
                .then();
    }

    @Step("Клиент - получение информации о товаре")
    public ValidatableResponse getItemById(String itemId) {
        return given()
                .filter(new AllureRestAssured())
                .get(ApiConstants.ITEM_TEST_URL + "/" + itemId)
                .then();
    }

    @Step("Клиент - получение списка товаров продавца")
    public ValidatableResponse getItemsListBySellerId(String sellerId) {
        return given()
                .filter(new AllureRestAssured())
                .get(ApiConstants.SELLER_TEST_URL + "/" + sellerId + "/item")
                .then();
    }

    @Step
    public ValidatableResponse getItemStatistic(String itemId) {
        return given()
                .filter(new AllureRestAssured())
                .get(ApiConstants.STATISTIC_TEST_URL + "/" + itemId)
                .then();
    }

}
