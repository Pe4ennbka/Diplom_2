import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestOrderCreate {
    ClientRequest clientRequest = new ClientRequest();
    ClientCheck clientCheck = new ClientCheck();
    private Client client;
    private ChangeClient changeClient;
    private Order order;



    @Before
    public void setUp() {
        client = Client.random();
        ValidatableResponse createResponse = clientRequest.createClient(client);
        clientCheck.createdClient(createResponse, client);

    }

    @Test
    @Description("Тест проверки запроса авторизованного пользователя с корректными ингредиентами")
    public void checkOrderAutoAndIngredient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        String ingredient = clientRequest.checkIngredient();

        ValidatableResponse createOrder = clientRequest.createOrderAuto(accessToken, order.random(ingredient));
        clientCheck.createOrder(createOrder);
    }
    @Test
    @Description("Тест проверки запроса не авторизованного пользователя с корректными ингредиентами")
    public void checkOrderNoAutoAndIngredient(){

        String ingredient = clientRequest.checkIngredient();

        ValidatableResponse createOrder = clientRequest.createOrderNoAuto(order.random(ingredient));
        clientCheck.noAuto(createOrder);
    }

    @Test
    @Description("Тест проверки запроса авторизованного пользователя без ингредиентав")
    public void checkOrderAutoAndWithoutIngredient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);


        ValidatableResponse createOrder = clientRequest.createWithoutOrderAuto(accessToken);
        clientCheck.createWithoutOrder(createOrder);
    }
    @Test
    @Description("Тест проверки запроса авторизованного пользователя с неправильным хешем ингредиента")
    public void checkOrderAutoAndIngredientBadHash(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        ValidatableResponse createOrder = clientRequest.createOrderAuto(accessToken, order.badHash());
        clientCheck.createOrderBadHash(createOrder);
    }



    @After
    public void setDown() {
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);
        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);
    }
}
