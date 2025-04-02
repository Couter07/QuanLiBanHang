package swing;

import java.util.ArrayList;
import java.util.List;

public class MilkTea extends Product {
    private List<String> topping;
    private int sugarLevel;
    private String iceLevel;

    public MilkTea(String idProduct, String name, String size, double price, String note, int sugarLevel, String iceLevel) {
        super(idProduct, name, size, price, note);
        this.topping = new ArrayList<>();
        this.sugarLevel = sugarLevel;
        this.iceLevel = iceLevel;
    }

    public List<String> getTopping() {
        return topping;
    }

    public int getSugarLevel() {
        return sugarLevel;
    }

    public String getIceLevel() {
        return iceLevel;
    }

    public void addTopping(String topping) {
        this.topping.add(topping);
    }

    @Override
    public String toString() {
        return "MilkTea{" +
                "idProduct='" + getIdProduct() + '\'' +
                ", name='" + getName() + '\'' +
                ", size='" + getSize() + '\'' +
                ", price=" + getPrice() +
                ", topping=" + topping +
                ", sugarLevel=" + sugarLevel +
                ", iceLevel='" + iceLevel + '\'' +
                ", note='" + getNote() + '\'' +
                '}';
    }
}
