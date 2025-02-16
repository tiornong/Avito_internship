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
@DisplayName("Негативные проверки поля price при создании товара")
public class ItemPriceParameterNegativeCasesTest {

    int itemPrice;

    public ItemPriceParameterNegativeCasesTest(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Parameterized.Parameters(name = "Проверяем цену -- {0}")
    public static Object[][] data() {
        return new Object[][]{
                {0},
                {-100},
        };
    }

    @Before
    public void setUp(){
        //для отображения в отчёте параметризованных данных
        String testName = String.format("Создание товара: параметр price %s", itemPrice);
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName(testName)
        );
    }

    @Test
    public void test() {
        ApiClient client = new ApiClient();
        ItemToSend testItem = ItemToSend.get();
        testItem.setPrice(itemPrice);

        ValidatableResponse response = client.createItem(testItem);

        response.assertThat().statusCode(SC_BAD_REQUEST);
    }

}
