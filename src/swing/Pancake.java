package swing;

public class Pancake extends Product {
    private boolean hasSyrup;
    private boolean hasButter;

    public Pancake(String idProduct, String name, String size, double price, String note, boolean hasSyrup, boolean hasButter) {
        super(idProduct, name, size, price, note);
        this.hasSyrup = hasSyrup;
        this.hasButter = hasButter;
    }

    public boolean isHasSyrup() {
        return hasSyrup;
    }

    public void setHasSyrup(boolean hasSyrup) {
        this.hasSyrup = hasSyrup;
    }

    public boolean isHasButter() {
        return hasButter;
    }

    public void setHasButter(boolean hasButter) {
        this.hasButter = hasButter;
    }

    @Override
    public String toString() {
        return "Pancake{" +
                "idProduct='" + getIdProduct() + '\'' +
                ", name='" + getName() + '\'' +
                ", size='" + getSize() + '\'' +
                ", price=" + getPrice() +
                ", hasSyrup=" + hasSyrup +
                ", hasButter=" + hasButter +
                ", note='" + getNote() + '\'' +
                '}';
    }
}
