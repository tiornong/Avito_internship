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

import java.util.regex.Pattern;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
@DisplayName("Позитивные проверки поля price при создании заказа")
public class ItemPriceParameterPositiveCasesTest {

    int itemPrice;
    private final Pattern answerPattern = Pattern.compile("Сохранили объявление - [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    public ItemPriceParameterPositiveCasesTest(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Parameterized.Parameters(name = "Проверяем цену -- {0}")
    public static Object[][] data() {
        return new Object[][]{
                {4321},
                {1},
                {1000000000},

        };
    }

    @Before
    public void setUp(){
        //для отображения в отчёте параметризованных данных
        String testName = String.format("Создание товара: параметр name %s", itemPrice);
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

        response.assertThat().statusCode(SC_OK);
        String answer = response.extract().path("status");
        assertThat(answerPattern.matcher(answer).matches(), equalTo(true));
    }

}
