package swing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String idOrder;
    private Date orderDate;
    private Customer customer;
    private double promotion;
    private double totalPrice;
    private List<OrderItem> items;

    public Order(String idOrder, Date orderDate, Customer customer, double promotion) {
        this.idOrder = idOrder;
        this.orderDate = orderDate;
        this.customer = customer;
        this.promotion = promotion;
        this.totalPrice = 0.0;
        this.items = new ArrayList<>();
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public void removeItem(OrderItem item) {
        this.items.remove(item);
    }

    public void clearItems() {
        this.items.clear();
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder='" + idOrder + '\'' +
                ", orderDate=" + orderDate +
                ", customer=" + customer +
                ", promotion=" + promotion +
                ", totalPrice=" + totalPrice +
                ", items=" + items +
                '}';
    }
}
