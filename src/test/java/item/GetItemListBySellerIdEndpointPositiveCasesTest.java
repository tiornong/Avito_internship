package item;

import helpers.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static config.ApiConstants.TEST_SELLER_ID;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;

@DisplayName("Позитивные тесты получения информации обо всех товарах продавца по его ID")
public class GetItemListBySellerIdEndpointPositiveCasesTest {

    @Test
    @DisplayName("Базовый позитивный кейс")
    @Description("Базовый позитивный кейс -- проверка того, что данные приходят, код и структура ответа корректны")
    public void basicCaseTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemsListBySellerId(TEST_SELLER_ID.toString());

        response.assertThat()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/items-info.json"));

    }


}
