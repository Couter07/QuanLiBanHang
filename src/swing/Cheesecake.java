package swing;

public class Cheesecake extends Product {
    private String flavor;
    private boolean hasTopping;

    public Cheesecake(String idProduct, String name, String size, double price, String note, String flavor, boolean hasTopping) {
        super(idProduct, name, size, price, note);
        this.flavor = flavor;
        this.hasTopping = hasTopping;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public boolean isHasTopping() {
        return hasTopping;
    }

    public void setHasTopping(boolean hasTopping) {
        this.hasTopping = hasTopping;
    }

    @Override
    public String toString() {
        return "Cheesecake{" +
                "idProduct='" + getIdProduct() + '\'' +
                ", name='" + getName() + '\'' +
                ", size='" + getSize() + '\'' +
                ", price=" + getPrice() +
                ", flavor='" + flavor + '\'' +
                ", hasTopping=" + hasTopping +
                ", note='" + getNote() + '\'' +
                '}';
    }
}
