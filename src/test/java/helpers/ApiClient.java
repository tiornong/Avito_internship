package helpers;

import config.ApiConstants;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.ValidatableResponse;
import model.ItemToSend;

import static io.restassured.RestAssured.given;

/**Класс для хранения методов, непосредственно взаимодействующих с API.*/
public class ApiClient {

    @Step("Клиент - создание товара")
    public ValidatableResponse createItem(ItemToSend item) {
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
                // replace используется на случай пустострокового sellerID
                .get(ApiConstants.SELLER_TEST_URL + ("/" + sellerId + "/item").replaceAll("//", "/"))
                .then();
    }

    @Step("Клиент - получение статистики товара")
    public ValidatableResponse getItemStatistic(String itemId) {
        return given()
                .filter(new AllureRestAssured())
                .get(ApiConstants.STATISTIC_TEST_URL + "/" + itemId)
                .then();
    }

}
