import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

public class TestClientChange {
    ClientRequest clientRequest = new ClientRequest();
    ClientCheck clientCheck = new ClientCheck();
    private Client client;
    private ChangeClient changeClient;



    @Before
    public void setUp() {
        client = Client.random();
        ValidatableResponse createResponse = clientRequest.createClient(client);
        clientCheck.createdClient(createResponse, client);

    }

    @Test
    @Description("Успешное изменения почты")
    public void changeEmailClient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        changeClient = ChangeClient.clientChangeEmail(client);
        ValidatableResponse changeResponse =clientRequest.changeAuto(accessToken, changeClient);
        clientCheck.changeFields(changeResponse, changeClient);

        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }
    @Test
    @Description("Успешное изменения пароля")
    public void changePasswordClient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        changeClient = ChangeClient.clientChangePassword(client);
        ValidatableResponse changeResponse =clientRequest.changeAuto(accessToken, changeClient);
        clientCheck.changeFields(changeResponse, changeClient);

        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }
    @Test
    @Description("Успешное изменения Имени")
    public void changeNameClient(){
        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);

        changeClient = ChangeClient.clientChangeName(client);
        ValidatableResponse changeResponse =clientRequest.changeAuto(accessToken, changeClient);
        clientCheck.changeFields(changeResponse, changeClient);

        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }
    @Test
    @Description("Не успешное изменения почты")
    public void errorChangeEmailClient(){

        changeClient = ChangeClient.clientChangeEmail(client);
        ValidatableResponse changeResponse =clientRequest.changeNoAuto(changeClient);
        clientCheck.noAuto(changeResponse);

        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);
        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }
    @Test
    @Description("Не успешное изменения пароля")
    public void errorChangePasswordClient(){

        changeClient = ChangeClient.clientChangePassword(client);
        ValidatableResponse changeResponse =clientRequest.changeNoAuto(changeClient);
        clientCheck.noAuto(changeResponse);

        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);
        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }
    @Test
    @Description("Не успешное изменения имени")
    public void errorChangeNameClient(){

        changeClient = ChangeClient.clientChangeName(client);
        ValidatableResponse changeResponse =clientRequest.changeNoAuto(changeClient);
        clientCheck.noAuto(changeResponse);

        var login = LoginClient.fromClient(client);
        ValidatableResponse loginResponse = clientRequest.loginClient(login);
        String accessToken = clientCheck.loginCourier(loginResponse);
        ValidatableResponse delete =  clientRequest.delete(accessToken);
        clientCheck.deleteClient(delete);

    }


}
