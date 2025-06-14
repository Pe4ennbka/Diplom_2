import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestClientLogin {
    ClientRequest clientRequest = new ClientRequest();
    ClientCheck clientCheck = new ClientCheck();
    private Client client;

    @Before
    public void setUp() {
        client = Client.random();
        ValidatableResponse createResponse = clientRequest.createClient(client);
        clientCheck.createdClient(createResponse, client);
    }
    @Test
    @Description("Успешно авторизался курьер")
    public void loginClient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        clientCheck.loginCourier(loginResponse);

    }
    @Test
    @Description("Ошибка авторизации при неверном Email")
    public void loginCourierWithoutEmail(){
        var login = LoginClient.clientWithoutEmail(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        clientCheck.loginWithIncorrectEmail(loginResponse);
    }
    @Test
    @Description("Ошибка авторизации при неверном пароле")
    public void loginCourierWithoutPassword(){
        var login = LoginClient.clientWithoutPassword(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        clientCheck.loginWithIncorrectEmail(loginResponse);
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
