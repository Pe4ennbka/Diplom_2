import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientRequest {
    @Step("Вызвать метод POST в создании клиента /api/auth/register")
    public ValidatableResponse createClient(Client client){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(client)
                .when()
                .post("/api/auth/register")
                .then().log().all();
    }
    @Step("Вызвать метод POST в залогинивании клиента /api/v1/courier/login")
    public  ValidatableResponse loginClient(LoginClient client){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(client)
                .when()
                .post("/api/auth/login")
                .then().log().all();
    }
    @Step("Вызвать метод DELETE у клиента /api/auth/user")
    public  ValidatableResponse delete(String accessToken){
        return given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .delete("/api/auth/user")
                .then().log().all();
    }
    @Step("Вызвать метод PATCH   у клиента /api/auth/user")
    public  ValidatableResponse changeAuto(String accessToken, ChangeClient changeClient){
        return given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(changeClient)
                .when()
                .patch("/api/auth/user")
                .then().log().all();
    }
    @Step("Вызвать метод PATCH   у клиента /api/auth/user")
    public  ValidatableResponse changeNoAuto(ChangeClient changeClient){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(changeClient)
                .when()
                .patch("/api/auth/user")
                .then().log().all();
    }
    @Step("Вытащить String ингридента в метода GET с ответом 200")
    public String checkIngredient(){
        int suff = new Random().nextInt(13) + 1;
        String ingredientId = given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .get("/api/ingredients")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("data[" + suff  + "]._id");

        assertThat(ingredientId, notNullValue());

        return ingredientId;
    }
    @Step("Проверка на создания заказа POST и получения 200 /api/orders")
    public ValidatableResponse createOrderAuto(String accessToken, Order order){
        return given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(order)
                .when()
                .post("/api/orders")
                .then().log().all();
    }
    @Step("Проверка на создания заказа POST без авторизации /api/orders")
    public ValidatableResponse createOrderNoAuto(Order order){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .body(order)
                .when()
                .post("/api/orders")
                .then().log().all();
    }
    @Step("Проверка на создания заказа POST без заказа /api/orders")
    public ValidatableResponse createWithoutOrderAuto(String accessToken){
        return given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .post("/api/orders")
                .then().log().all();
    }
    @Step("Проверка на создания заказа GET получение заказов конкретного авторизованного пользователя /api/orders ")
    public ValidatableResponse showOrderUserAuto(String accessToken){
        return given().log().all()
                .header("Authorization", accessToken)
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .get("/api/orders")
                .then().log().all();
    }
    @Step("Проверка на создания заказа GET получение заказов конкретного не авторизованного пользователя /api/orders ")
    public ValidatableResponse showOrderUserNoAuto(){
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .get("/api/orders")
                .then().log().all();
    }
}

