package item.postitem;

import helpers.ApiClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ItemToSend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
@DisplayName("Негативные проверки поля name при создании товара")
public class ItemNameParameterNegativeCasesTest {


    String itemName;

    public ItemNameParameterNegativeCasesTest(String itemName) {
        this.itemName = itemName;
    }

    @Parameterized.Parameters(name = "Проверяем имя -- {0}")
    public static Object[][] testData() {
        return new Object[][] {
                {"Тим#фей"},
                {"Тим0фей"},
                {""},
        };
    }

    @Before
    public void setUp(){
        //для отображения в отчёте параметризованных данных
        String testName = String.format("Создание товара: параметр name %s", itemName);
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName(testName)
        );
    }


    @Test
    public void test() {
        ApiClient client = new ApiClient();
        ItemToSend testItem = ItemToSend.get();
        testItem.setName(itemName);

        ValidatableResponse response = client.createItem(testItem);

        response.assertThat().statusCode(SC_BAD_REQUEST);
    }

}
