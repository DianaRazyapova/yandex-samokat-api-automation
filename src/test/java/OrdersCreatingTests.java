import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.services.praktikum.scooter.qa.Orders;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class OrdersCreatingTests {


    private final Orders orders;

    public OrdersCreatingTests(Orders orders) {
        this.orders = orders;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}")
    public static Object[][] getTestData() {
        return new Object[][]{{new Orders("Енгар", "Котик", "ул.Котиков 10", "Комсомольская", "89995001020", 3, "2023-04-12", "У меня лапки", new String[]{"BLACK"})}, {new Orders("Изюм", "Шпиц", "ул.Собачек 11", "Комсомольская", "89995001021", 5, "2023-04-14", "Хороший мальчик ждет самокат!", new String[]{"GREY"})}, {new Orders("Дарина", "Тимергалина", "ул.Комсомольская 11", "Комсомольская", "89995001023", 7, "2023-04-11", "", new String[]{"BLACK", "GREY"})}, {new Orders("Аврора", "Фролова", "ул.Проспект Октября 23", "Спортивная", "89995001025", 3, "2023-04-11", "", new String[]{})},};
    }

    @Test
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с различными данными")
    public void checkCreateOrder() {
        Response response = given().log().all().header("Content-type", "application/json").body(orders).when().post("/api/v1/orders");
        response.then().log().all().assertThat().and().statusCode(201).body("track", Matchers.notNullValue());
    }
}
