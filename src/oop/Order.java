package oop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String idOrder;
    private Date orderDate;
    private Customer customer;
    private double promotion, totalPrice;
    private List<OrderItem> listOrderItem;

    public Order() {
        listOrderItem = new ArrayList<OrderItem>();
    }


}
