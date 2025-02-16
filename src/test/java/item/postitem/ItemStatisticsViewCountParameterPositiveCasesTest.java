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

import java.util.regex.Pattern;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
@DisplayName("Позитивные проверки подпараметра ViewCount при создании товара")
public class ItemStatisticsViewCountParameterPositiveCasesTest {

    int itemViewCount;
    private final Pattern answerPattern = Pattern.compile("Сохранили объявление - [0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    @Parameterized.Parameters(name = "Проверяем просмотры -- {0}")
    public static Object[][] data() {
        return new Object[][]{
                {0},
                {1},
                {1234},
                {1000000000},
        };
    }

    public ItemStatisticsViewCountParameterPositiveCasesTest(int itemViewCount) {
        this.itemViewCount = itemViewCount;
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

        response.assertThat().statusCode(SC_OK);
        String answer = response.extract().path("status");
        assertThat(answerPattern.matcher(answer).matches(), equalTo(true));

    }

}

