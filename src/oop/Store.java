package oop;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Order> listOrder;
    private List<Product> listProduct;
    private List<Customer> listCustomer;
    public Store(){
        listOrder = new ArrayList<Order>();
        listProduct = new ArrayList<Product>();
        listCustomer = new ArrayList<Customer>();

//        listProduct.add(new Product("101", "Cà phê sữa", "M", 30.000));
//        listProduct.add(new Product("102", "Trà đào", "L", 35.000));
//        listProduct.add(new Product("103", "Bạc xỉu", "S", 25.000));
    }
    public Product findProductById(String id) {
        for (Product product : listProduct) {
            if (product.getIdProduct().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
