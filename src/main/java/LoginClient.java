    import java.util.Random;

    public class LoginClient {

        private final String email;
        private final String password;

        public LoginClient(String email, String password){
            this.email = email;
            this.password = password;

        }

        public static LoginClient fromClient(Client client){
            return new LoginClient(client.getEmail(), client.getPassword());
        }
        public static LoginClient fromChangeClient(ChangeClient changeClient){
            return new LoginClient(changeClient.getEmail(), changeClient.getPassword());
        }

        public static LoginClient clientWithoutEmail(Client client){
            return new LoginClient("error@mail.ru", client.getPassword());
        }
        public static LoginClient clientWithoutPassword(Client client){
            return new LoginClient(client.getEmail(), "qwerr");
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }
