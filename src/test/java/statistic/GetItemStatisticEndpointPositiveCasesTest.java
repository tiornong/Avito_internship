package statistic;

import helpers.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ItemToSend;
import model.Statistics;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@DisplayName("Тесты получения статистики товара")
public class GetItemStatisticEndpointPositiveCasesTest {

    String itemId;

    @Before
    @Step("Создание заказа перед запросом его данных")
    public void setUp() {

        // Создаём шаблонный товар
        ItemToSend item = ItemToSend.get();
        ApiClient client = new ApiClient();

        // Получаем и сохраняем АйДи заказа
        itemId = client.createItem(item).extract().path("status").toString().split(" - ")[1];
    }

    @Test
    @DisplayName("Базовый позитивный кейс")
    @Description("Базовый позитивный кейс -- проверка того, что данные приходят, код и структура ответа корректны")
    public void basicCaseTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemStatistic(itemId);

        response.assertThat()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/item-statistics.json"));

    }

    @Test
    @DisplayName("Параметр statistics")
    @Description("Проверка того, что приходящее нам значение параметра statistics соответствует тому, который мы отправили.")
    public void correctDataTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemStatistic(itemId);

        // Т.к. при создании товара статистика передается объектом, нет особого смысла тестировать вложенные параметры
        assertThat(response.extract().jsonPath().getList("", Statistics.class).get(0), equalTo(Statistics.get()));

    }

}
