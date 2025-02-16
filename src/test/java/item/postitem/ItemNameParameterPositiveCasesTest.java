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
import static org.hamcrest.Matchers.equalTo;

import java.util.regex.Pattern;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
@DisplayName("Позитивные проверки поля name при создании заказа")
public class ItemNameParameterPositiveCasesTest {

    String itemName;
    private final Pattern answerPattern = Pattern.compile("Сохранили объявление - [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    public ItemNameParameterPositiveCasesTest(String itemName) {
        this.itemName = itemName;
    }

    @Parameterized.Parameters(name = "Проверяем имя -- {0}")
    public static Object[][] testData() {
        return new Object[][] {
                {"Машина"},
                {"Ма-ши-на"},
                {"Маш ина"},
                {"машина"},
                {"МАШИНА"},
                {"car"},
                {"Car"},
                {"CAR"},
                {"Щекочихинкрествовоздвиженский Парк со всеми внутренними аттракционами и прочим стаффом"},
                {"Ио"},
                {"Новая car"}
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

        response.assertThat().statusCode(SC_OK);
        String answer = response.extract().path("status");
        assertThat(answerPattern.matcher(answer).matches(), equalTo(true));
    }

}
