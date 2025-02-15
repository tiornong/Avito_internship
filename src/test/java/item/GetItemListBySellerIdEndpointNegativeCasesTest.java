package item;

import helpers.ApiClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import org.junit.Test;

import static config.ApiConstants.TEST_SELLER_ID;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@DisplayName("Негативные тесты получения информации обо всех товарах продавца по его ID")
public class GetItemListBySellerIdEndpointNegativeCasesTest {

    @Test
    @DisplayName("sellerID с латынью")
    @Description("Попытка получить данные по некорректному sellerID (содержит латынь)")
    public void latinSellerIdTest(){
        Faker faker = new Faker();
        String replacementChar = faker.regexify("[qwertyuiopasdfghjklzxcvbnm]{1}");
        String sellerId = TEST_SELLER_ID.toString().replaceAll(".$", replacementChar);

        ApiClient client = new ApiClient();

        ValidatableResponse response = client.getItemsListBySellerId(sellerId);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("sellerID с кириллицей")
    @Description("Попытка получить данные по некорректному sellerID (содержит кириллицу)")
    public void cyrillicSellerIdTest(){
        Faker faker = new Faker();
        String replacementChar = faker.regexify("[йцукенгшщхъфывапролджэячсмитьбю]{1}");
        String sellerId = TEST_SELLER_ID.toString().replaceAll(".$", replacementChar);

        ApiClient client = new ApiClient();

        ValidatableResponse response = client.getItemsListBySellerId(sellerId);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("sellerID со спецсимволами")
    @Description("Попытка получить данные по некорректному sellerID (содержит спецсимволы)")
    public void specialCharSellerIdTest(){
        Faker faker = new Faker();
        String replacementChar = faker.regexify("[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]{1}");
        String sellerId = TEST_SELLER_ID.toString().replaceAll(".$", replacementChar);

        ApiClient client = new ApiClient();

        ValidatableResponse response = client.getItemsListBySellerId(sellerId);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Не передача sellerID")
    @Description("Попытка получить данные при не передаче sellerID")
    public void lackOfSellerIdTest(){
        ApiClient client = new ApiClient();
        ValidatableResponse response = client.getItemsListBySellerId("");
        response.assertThat()
                .statusCode(SC_BAD_REQUEST);
    }

}
