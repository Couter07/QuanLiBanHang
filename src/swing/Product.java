package swing;

public class Product {
    private String idProduct;
    private String name;
    private String size;
    private double price;
    private String note;

    public Product(String idProduct, String name, String size, double price, String note) {
        this.idProduct = idProduct;
        this.name = name;
        this.size = size;
        this.price = price;
        this.note = note;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct='" + idProduct + '\'' +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", note='" + note + '\'' +
                '}';
    }
}
