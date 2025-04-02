package swing;

public class MatchaLatte extends Product {
    private String milkType;

    public MatchaLatte(String idProduct, String name, String size, double price, String note, String milkType) {
        super(idProduct, name, size, price, note);
        this.milkType = milkType;
    }

    public String getMilkType() {
        return milkType;
    }

    public void setMilkType(String milkType) {
        this.milkType = milkType;
    }

    @Override
    public String toString() {
        return "MatchaLatte{" +
                "idProduct='" + getIdProduct() + '\'' +
                ", name='" + getName() + '\'' +
                ", size='" + getSize() + '\'' +
                ", price=" + getPrice() +
                ", milkType='" + milkType + '\'' +
                ", note='" + getNote() + '\'' +
                '}';
    }
}
