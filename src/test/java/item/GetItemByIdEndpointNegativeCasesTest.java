package item;

import helpers.ApiClient;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

@DisplayName("Негативныетесты получения информации о заказе через его ID")
public class GetItemByIdEndpointNegativeCasesTest {

    @Test
    @DisplayName("Базовый негативный кейс")
    @Description("Базовый негативный кейс -- проверка того, что запрос с несуществующим ID товара возвращает корректный код")
    public void basicCaseTest(){
        ApiClient client = new ApiClient();

        String itemId = UUID.randomUUID().toString();
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Отсутствие ID товара")
    @Description("Проверка кода ответа при не передаче ID товара в запросе")
    public void lackOfItemIdTest(){
        ApiClient client = new ApiClient();

        String itemId = "";
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("ID товара короче необходимого")
    @Description("Проверка кода ответа при передаче укороченного ID товара")
    public void shortIdTest(){
        ApiClient client = new ApiClient();

        String itemId = UUID.randomUUID().toString().substring(1);
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("ID товара длиннее необходимого")
    @Description("Проверка кода ответа при передаче удлинённого ID товара")
    public void longIdTest(){
        ApiClient client = new ApiClient();

        String itemId = UUID.randomUUID() + Integer.toHexString(new Random().nextInt(17));
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Передача некорректной структуры ID товара")
    @Description("Проверка кода ответа при передаче ID товара с не соблюдённой структурой UUID")
    public void brokenStructureIdTest(){
        ApiClient client = new ApiClient();

        String itemId = UUID.randomUUID().toString().replaceAll("-", "");
        ValidatableResponse response = client.getItemById(itemId);

        // Автоматически структура ответа правится, но заказ найти не должно
        response.assertThat()
                .statusCode(SC_NOT_FOUND);

    }

    @Test
    @DisplayName("Наличие спецсимвола в ID товара")
    @Description("Проверка кода ответа при передаче ID товара со спецсимволом в составе")
    public void specialCharInIdTest(){
        ApiClient client = new ApiClient();

        Faker faker = new Faker();
        String replacementChar = faker.regexify("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]{1}");
        String itemId = UUID.randomUUID().toString().replaceAll(".$", replacementChar);
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Наличие латинской буквы в ID товара")
    @Description("Проверка кода ответа при передаче ID товара с латинской буквой, выходящей за пределы 16-ричной системы счисления, в составе")
    public void nonHexCharInIdTest(){
        ApiClient client = new ApiClient();

        Faker faker = new Faker();
        String replacementChar = faker.regexify("[qwrtyuiopsghjklzxvnm]{1}");
        String itemId = UUID.randomUUID().toString().replaceAll(".$", replacementChar);
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Наличие кириллической буквы в ID товара")
    @Description("Проверка кода ответа при передаче ID товара с кириллической буквой, входящей, теоретически, в 16-ричную систему счисления, в составе")
    public void nonLatinCharInIdTest(){
        ApiClient client = new ApiClient();

        Faker faker = new Faker();
        String replacementChar = faker.regexify("[абвгдеж]{1}");
        String itemId = UUID.randomUUID().toString().replaceAll(".$", replacementChar);
        ValidatableResponse response = client.getItemById(itemId);

        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

   //@Test
   //@DisplayName("")
   //@Description("")
   //public void Test(){
   //    ApiClient client = new ApiClient();
   //
   //    String itemId = UUID.randomUUID().toString();
   //    ValidatableResponse response = client.getItemById(itemId);
   //
   //    response.assertThat()
   //            .statusCode(SC_BAD_REQUEST);
   //}
}
