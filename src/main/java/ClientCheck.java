import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.*;

public class ClientCheck {
    @Step("Проверка на создения клиета (200) и получении статуса  ok: 200")
    public String createdClient(ValidatableResponse response, Client client) {
        String accessToken = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("success", equalTo(true))
                .body("user.email", equalTo(client.getEmail()))
                .body("user.name", equalTo(client.getName()))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .extract()
                .path("accessToken");
        ;
        return accessToken;

    }
    @Step("Проверка на регистрацию уже имеющиегося аккаунта (403) и получении статуса  Forbidden: 403")
    public void createdDoubleClient(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("message", equalTo("User already exists"))
                .extract()
                .path("success");
        ;
        Assert.assertFalse(success);
    }
    @Step("Проверка на регистрацию без заполнения одного поля (403) и получении статуса  Forbidden: 403")
    public void createdWithoutField(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_FORBIDDEN)
                .body("message", equalTo("Email, password and name are required fields"))
                .extract()
                .path("success");
        ;
        Assert.assertFalse(success);
    }
    @Step("Проверка на получение id (200) и получении id и номер")
    public String loginCourier(ValidatableResponse response) {
        String accessToken = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .extract()
                .path("accessToken");
        ;
        return accessToken;
    }
    @Step("Проверка на удаления клиента и полчуения 202 ответа")
    public void deleteClient(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_ACCEPTED)
                .body("message", equalTo("User successfully removed"))
                .extract()
                .path("success");
        ;
        Assert.assertTrue(success);
    }
    @Step("Проверка на удаления клиента и полчуения 401 ответа")
    public void loginWithIncorrectEmail(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("message", equalTo("email or password are incorrect"))
                .extract()
                .path("success");
        ;
        Assert.assertFalse( success);
    }
    @Step("Проверка на изменения полей и получения 200")
    public void changeFields(ValidatableResponse response, ChangeClient changeClient) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("user.email", equalTo(changeClient.getEmail()))
                .body("user.name", equalTo(changeClient.getName()))
                .extract()
                .path("success");
        ;
        Assert.assertTrue( success);
    }
    @Step("Не авторизованного пользователь выдает ошибку")
    public void noAuto(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_UNAUTHORIZED)
                .body("message", equalTo("You should be authorised"))
                .extract()
                .path("success");
        ;
        Assert.assertFalse(success);
    }
    @Step("Проверка на создания заказа POST и получения 200")
    public void createOrder(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("name", notNullValue())
                .body("order.number", notNullValue())
                .body("order", not(hasKey("ingredients")))
                .extract()
                .path("success");
        ;
        Assert.assertTrue( success);
    }
    @Step("Проверка на создания заказа POST без тела и получения 400")
    public void createWithoutOrder(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Ingredient ids must be provided"))
                .extract()
                .path("success");
        ;
        Assert.assertFalse(success);
    }
    @Step("Проверка на создания заказа POST с не правильным хешем и получения 500")
    public void createOrderBadHash(ValidatableResponse response) {
         response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
        ;
    }
    @Step("Проверка на показания заказав с ответом 200 ")
    public void showOrderAuto(ValidatableResponse response) {
        boolean success = response
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("total", notNullValue())
                .body("totalToday", notNullValue())
                .body("order", (hasKey("ingredients")))
                .extract()
                .path("success");
        ;
        Assert.assertTrue(success);
    }



}
