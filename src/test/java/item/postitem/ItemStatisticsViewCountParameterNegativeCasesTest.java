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
@DisplayName("Негативные тесты подпараметра ViewCount при создании товара")
public class ItemStatisticsViewCountParameterNegativeCasesTest {

    int itemViewCount;

    public ItemStatisticsViewCountParameterNegativeCasesTest(int itemViewCount) {
        this.itemViewCount = itemViewCount;
    }

    @Parameterized.Parameters(name = "Проверяем просмотры -- {0}")
    public static Object[][] data() {
        return new Object[][]{
                {-1000},
        };
    }

    @Before
    public void setUp() {
        //для отображения в отчёте параметризованных данных
        String testName = String.format("Создание товара: подпараметр viewCount %s", itemViewCount);
        Allure.getLifecycle().updateTestCase(testResult ->
                testResult.setName(testName)
        );
    }


    @Test
    public void test() {
        ApiClient client = new ApiClient();
        Statistics testStatistics = Statistics.get();
        testStatistics.setViewCount(itemViewCount);
        ItemToSend testItem = ItemToSend.get(testStatistics);

        ValidatableResponse response = client.createItem(testItem);

        response.assertThat().statusCode(SC_BAD_REQUEST);
    }

}
