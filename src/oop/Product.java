package oop;

public abstract class Product {
    private String idProduct, name, size;
    private double price;

    public Product(String idProduct,String name, String size,double price) {
        this.idProduct = idProduct;
        this.name = name;
        this.size = size;
        this.price = price;
    }
    public String getIdProduct(){
        return idProduct;
    }
}
