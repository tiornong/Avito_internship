package item;

import helpers.ApiClient;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.UUID;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class GetItemByIdEndpointNegativeCasesTest {

    @Test
    @DisplayName("Базовый негативный кейс")
    @Description("Базовый негативный кейс -- проверка того, что запрос с несуществующим ID товара возвращает корректный код")
    public void basicCaseTest(){
        ApiClient client = new ApiClient();
        //
        String itemId = UUID.randomUUID().toString();
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_NOT_FOUND);

    }

}
