
import io.restassured.response.ValidatableResponse;
import jdk.jfr.Description;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class TestClientCreate {
        ClientRequest clientRequest = new ClientRequest();
        ClientCheck clientCheck = new ClientCheck();

        @Test
        @Description("Создании курьера")
        public void createClient() {
                var client = Client.random();
                ValidatableResponse createResponse = clientRequest.createClient(client);
                String accessToken = clientCheck.createdClient(createResponse, client);

                ValidatableResponse delete =  clientRequest.delete(accessToken);
                clientCheck.deleteClient(delete);

        }
        @Test
        @Description("Ошибка при создании двух одинаковых курьеров")
        public void conflictCreatDoubleClient() {
                var client = Client.random();
                ValidatableResponse createResponse = clientRequest.createClient(client);
                String accessToken = clientCheck.createdClient(createResponse, client);

                ValidatableResponse createResponse1 = clientRequest.createClient(client);
                clientCheck.createdDoubleClient(createResponse1);

                ValidatableResponse delete =  clientRequest.delete(accessToken);
                clientCheck.deleteClient(delete);
        }
        @Test
        @Description("Ошибка при не заполнении поля email")
        public void loginClientWrongEmail() {
                var client = Client.withoutEmail();
                ValidatableResponse createResponse = clientRequest.createClient(client);
                clientCheck.createdWithoutField(createResponse);
        }
        @Test
        @Description("Ошибка при не заполнении поля пароля")
        public void loginClientWrongPassword() {
                var client = Client.withoutPassword();
                ValidatableResponse createResponse = clientRequest.createClient(client);
                clientCheck.createdWithoutField(createResponse);
        }
        @Test
        @Description("Ошибка при не заполнении поля имени")
        public void loginClientWrongName() {
                var client = Client.withoutName();
                ValidatableResponse createResponse = clientRequest.createClient(client);
                clientCheck.createdWithoutField(createResponse);
        }


}
