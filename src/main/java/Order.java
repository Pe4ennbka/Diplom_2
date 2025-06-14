import java.util.Arrays;
import java.util.List;

public class Order {
    private final List<String> ingredients;
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order random(String Bun){
        return new Order(Arrays.asList(Bun));
    }
    public static Order badHash(){
        return new Order(Arrays.asList("61c65a71d1f82901bdaaa70"));
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
