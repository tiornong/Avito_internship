package item.postitem;

import config.ApiConstants;
import helpers.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ItemToSend;
import model.Statistics;
import net.datafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.apache.http.HttpStatus.SC_CREATED;

@DisplayName("Тесты создания товара")
public class PostItemEndpointBaseTest {

    private String name;
    private int price;
    private Statistics statistics;

    @Before
    @Step("Создание тестовых данных")
    public void setUp() {

        //Cоздаём рандомизированные данные товара и сохраняем для дальнейших проверок
        long seed = new Random().nextLong();
        Faker faker = new Faker(new Random(seed));

        this.name = faker.lorem().characters(10);
        this.price = new Random().nextInt(100);

        Integer contacts = new Random().nextInt(100);
        Integer likes = new Random().nextInt(100);
        Integer viewCount = new Random().nextInt(100);
        this.statistics = new Statistics(contacts, likes, viewCount);

    }

    @Test
    @DisplayName("Базовый позитивный тест")
    @Description("Базовый кейс -- проверка работы ручки при всех корректных параметрах, корректности кода ответа и корректности структуры тела ответа.")
    public void baseTest(){
        ItemToSend item = new ItemToSend(ApiConstants.TEST_SELLER_ID, name, price, statistics);
        ApiClient client = new ApiClient();
        ValidatableResponse response = client.createItem(item);
        response.assertThat().statusCode(SC_CREATED);
    }



}
