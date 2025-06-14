import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestShowOrderUser {
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
    @Description("Тест проверки просмотр сделанного заказа авторизованного пользователя")
    public void showOrderUserAuto(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        String ingredient = clientRequest.checkIngredient();

        ValidatableResponse createOrder = clientRequest.createOrderAuto(accessToken, order.random(ingredient));
        clientCheck.createOrder(createOrder);

        ValidatableResponse showOrder = clientRequest.showOrderUserAuto(accessToken);
        clientCheck.showOrderAuto(showOrder);
    }
    @Test
    @Description("Тест проверки просмотр сделанного заказа не авторизованного пользователя")
    public void showOrderUserNoAuto(){
        ValidatableResponse showOrder = clientRequest.showOrderUserNoAuto();
        clientCheck.noAuto(showOrder);
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
