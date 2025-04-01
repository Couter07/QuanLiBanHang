package swing;

import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, Food> foodMenu;

    public Menu() {
        foodMenu = new HashMap<>();
        foodMenu.put("001", new Food("Phở", "Món súp nước nổi tiếng với bánh phở và thịt bò."));
        foodMenu.put("002", new Food("Bánh mì", "Món ăn nhanh với bánh mì kẹp thịt, rau và gia vị."));
        foodMenu.put("003", new Food("Gỏi cuốn", "Món ăn nhẹ với tôm, rau và bún cuốn trong bánh tráng."));
    }

    public Food getFoodById(String id) {
        return foodMenu.get(id);
    }

    static class Food {
        String name, description;
        Food(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
