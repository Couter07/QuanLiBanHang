package swing;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Order> orders;
    private List<Product> products;
    private List<Customer> customers;

    public Store() {
        this.orders = new ArrayList<>();
        this.products = new ArrayList<>();
        this.customers = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        List<Order> customerOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getCustomer().equals(customer)) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addOrder(Order order) {
        if (getOrderById(order.getIdOrder()) == null) {
            this.orders.add(order);
        } else {
            System.out.println("Order already exists.");
        }
    }

    public void addProduct(Product product) {
        if (getProductById(product.getIdProduct()) == null) {
            this.products.add(product);
        } else {
            System.out.println("Product already exists.");
        }
    }

    public void addCustomer(Customer customer) {
        if (getCustomerByPhone(customer.getNumberPhone()) == null) {
            this.customers.add(customer);
        } else {
            System.out.println("Customer already exists.");
        }
    }

    public Product getProductById(String productId) {
        for (Product product : products) {
            if (product.getIdProduct() != null && product.getIdProduct().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Customer getCustomerByPhone(String numberPhone) {
        for (Customer customer : customers) {
            if (customer.getNumberPhone() != null && customer.getNumberPhone().equals(numberPhone)) {
                return customer;
            }
        }
        return null;
    }

    public Order getOrderById(String orderId) {
        for (Order order : orders) {
            if (order.getIdOrder() != null && order.getIdOrder().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Store{" +
                "orders=" + orders +
                ", products=" + products +
                ", customers=" + customers +
                '}';
    }
}
