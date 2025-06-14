import java.util.Random;

public class Client {

    private final String email;
    private final String password;
    private final String name;

    public Client(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public static Client random(){
        int suff = new Random().nextInt(1000000);
        return new Client("kolia" + suff + "@yandex.com", "12345", "Nikolai");
    }
    public static Client withoutEmail(){
        return new Client("", "12345", "Nikolai");
    }
    public static Client withoutPassword(){
        int suff = new Random().nextInt(1000000);
        return new Client("kolia" + suff + "@yandex.com", "", "Nikolai");
    }
    public static Client withoutName(){
        int suff = new Random().nextInt(1000000);
        return new Client("kolia" + suff + "@yandex.com", "12345", "");
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
