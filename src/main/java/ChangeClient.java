import java.util.Random;

public class ChangeClient {

    private String email;
    private String password;
    private String name;

    public ChangeClient(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public static ChangeClient clientChangeEmail(Client client){
        int suff = new Random().nextInt(1000000);
        return new ChangeClient("koliaa" + suff + "@yandex.com", client.getPassword(), client.getName());
    }
    public static ChangeClient clientChangePassword(Client client){
        return new ChangeClient(client.getEmail(), "rewq", client.getName());
    }
    public static ChangeClient clientChangeName(Client client){
        return new ChangeClient(client.getEmail(), client.getPassword(), "Kolia");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
