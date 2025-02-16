package item.postitem;

import helpers.ApiClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.ItemToSend;
import model.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
@DisplayName("Негативные тесты подпараметра contacts при создании товара")
public class ItemStatisticsContactsParameterNegativeCasesTest {

    int itemContacts;

    public ItemStatisticsContactsParameterNegativeCasesTest(int itemContacts) {
        this.itemContacts = itemContacts;
    }

    @Parameterized.Parameters(name = "Проверяем контакты -- {0}")
    public static Object[][] data() {
        return new Object[][]{
                {-1000},
        };
    }

    @Before
    public void setUp() {
        //для отображения в отчёте параметризованных данных
        String testName = String.format("Создание товара: подпараметр contacts %s", itemContacts);
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName(testName)
        );
    }


    @Test
    public void test() {
        ApiClient client = new ApiClient();
        Statistics testStatistics = Statistics.get();
        testStatistics.setContacts(itemContacts);
        ItemToSend testItem = ItemToSend.get(testStatistics);

        ValidatableResponse response = client.createItem(testItem);

        response.assertThat().statusCode(SC_BAD_REQUEST);
    }

}
