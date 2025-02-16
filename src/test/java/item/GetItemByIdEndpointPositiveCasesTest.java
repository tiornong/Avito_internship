package item;

import config.ApiConstants;
import helpers.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ItemToGet;
import model.ItemToSend;
import model.Statistics;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DisplayName("Позитивные тесты получения информации о заказе через его ID")
public class GetItemByIdEndpointPositiveCasesTest {

    private String itemId;
    private String name;
    private int price;
    private Statistics statistics;



    @Before
    @Step("Создание заказа перед запросом его данных")
    public void setUp() {

        //Cоздаём рандомизированные данные товара и сохраняем для дальнейших проверок
        long seed = new Random().nextLong();
        Faker faker = new Faker(new Random(seed));

        this.name = faker.lorem().characters(10);
        this.price = new Random().nextInt(10000);

        int contacts = new Random().nextInt(10000);
        int likes = new Random().nextInt(10000);
        int viewCount = new Random().nextInt(10000);
        this.statistics = new Statistics(contacts, likes, viewCount);

        // Создаём рандомизированный товар
        ItemToSend item = new ItemToSend(ApiConstants.TEST_SELLER_ID, name, price, statistics);
        ApiClient client = new ApiClient();

        // Получаем и сохраняем АйДи заказа
        itemId = client.createItem(item).extract().path("status").toString().split(" - ")[1];
    }

    @Test
    @DisplayName("Базовый позитивный кейс")
    @Description("Базовый позитивный кейс -- проверка того, что данные приходят, код и структура ответа корректны")
    public void basicCaseTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/items-info.json"));

    }

    @Test
    @DisplayName("Параметр id")
    @Description("Проверка того, что приходящее нам значение параметра id соответствует тому, который мы отправили")
    public void idTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);
        assertThat(response.extract().path("[0].id"), equalTo(itemId));

    }

    @Test
    @DisplayName("Параметр name")
    @Description("Проверка того, что приходящее нам значение параметра name соответствует тому, который мы отправили")
    public void nameTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);
        assertThat(response.extract().path("[0].name"), equalTo(name));

    }

    @Test
    @DisplayName("Параметр price")
    @Description("Проверка того, что приходящее нам значение параметра price соответствует тому, который мы отправили")
    public void priceTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);
        assertThat(response.extract().path("[0].price"), equalTo(price));

    }

    @Test
    @DisplayName("Параметр sellerId")
    @Description("Проверка того, что приходящее нам значение параметра sellerId соответствует тому, который мы отправили")
    public void sellerIdTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);
        assertThat(response.extract().path("[0].sellerId"), equalTo(ApiConstants.TEST_SELLER_ID));

    }

    @Test
    @DisplayName("Параметр statistics")
    @Description("Проверка того, что приходящее нам значение параметра statistics соответствует тому, который мы отправили.")
    public void statisticsTest(){

        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemById(itemId);

        // Т.к. при создании товара статистика передается объектом, нет особого смысла тестировать вложенные параметры
        assertThat(response.extract().jsonPath().getList("", ItemToGet.class).get(0).getStatistics(), equalTo(statistics));

    }


}
