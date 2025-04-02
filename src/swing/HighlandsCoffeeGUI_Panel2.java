package swing;

import swing.Store;
import swing.Customer;
import swing.Order;
import swing.OrderItem;
import swing.Product;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HighlandsCoffeeGUI_Panel2 extends JPanel {
    private Store store;
    private JTable customerTable;
    private JTable orderTable;
    private JTable orderItemTable;
    private JTextArea totalRevenueTextArea;
    private DefaultTableModel orderTableModel;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.##");

    public HighlandsCoffeeGUI_Panel2(Store store) {
        this.store = store;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left Panel: Customer List
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel customerListPanel = createCustomerPanel();
        leftPanel.add(customerListPanel, BorderLayout.NORTH);

        // Right Panel: Order List and Order Item Details
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel orderListPanel = createOrderPanel();
        JPanel orderItemPanel = createOrderItemPanel();
        rightPanel.add(orderListPanel, BorderLayout.NORTH);
        rightPanel.add(orderItemPanel, BorderLayout.CENTER);

        // Add revenue panel to south
        JPanel revenuePanel = createRevenuePanel();
        rightPanel.add(revenuePanel, BorderLayout.SOUTH);

        // Add leftPanel and rightPanel to mainContentPanel
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.add(leftPanel, BorderLayout.WEST);
        mainContentPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainContentPanel, BorderLayout.CENTER);

        // Initialize Data (should be called by the main GUI)
        customerTable.setRowSelectionAllowed(true);

        // Init customer selection listener (should be set by the main GUI)
    }

    public void addCustomerSelectionListener(ListSelectionListener listener) {
        customerTable.getSelectionModel().addListSelectionListener(listener);
    }

    public void addOrderSelectionListener(ListSelectionListener listener) {
        orderTable.getSelectionModel().addListSelectionListener(listener);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));

        String[] columnNames = {"Tên", "Số điện thoại"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        customerTable = new JTable(model);
        Main.styleTable(customerTable); // Apply style
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(new Dimension(300, 550));

        return panel;
    }

    private JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Danh sách hóa đơn"));

        String[] columnNames = {"ID", "Ngày", "Tổng"};
        Object[][] data = {};
        orderTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderTable = new JTable(orderTableModel);
        Main.styleTable(orderTable); // Apply style
        JScrollPane scrollPane = new JScrollPane(orderTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(new Dimension(600, 275));

        return panel;
    }

    private JPanel createOrderItemPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

        String[] columnNames = {"ID", "Tên", "Kích cỡ", "SL", "Giá", "Thành tiền", "Ghi chú"};
        Object[][] data = {};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        orderItemTable = new JTable(model);
        Main.styleTable(orderItemTable); // Apply style
        JScrollPane scrollPane = new JScrollPane(orderItemTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(new Dimension(600, 275));

        return panel;
    }

    private JPanel createRevenuePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Tổng doanh thu"));
        panel.setPreferredSize(new Dimension(600, 100));
        totalRevenueTextArea = new JTextArea();
        totalRevenueTextArea.setEditable(false);
        panel.add(totalRevenueTextArea, BorderLayout.CENTER);
        return panel;
    }

    public void updateCustomerTable(List<Customer> customers) {
        Object[][] data = new Object[customers.size()][2];
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getName();
            data[i][1] = customer.getNumberPhone();
        }

        String[] columnNames = {"Tên", "Số điện thoại"};
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        model.setDataVector(data, columnNames);
    }

    public void updateOrderTable(List<Order> orders) {
        Object[][] data = new Object[orders.size()][3];
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            data[i][0] = order.getIdOrder();
            data[i][1] = dateFormat.format(order.getOrderDate());
            data[i][2] = currencyFormat.format(order.getTotalPrice());
        }

        String[] columnNames = {"ID", "Ngày", "Tổng"};
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.setDataVector(data, columnNames);
        if (orders.size() > 0) {
            orderTable.setRowSelectionInterval(0, 0);
        } else {
            DefaultTableModel orderItemModel = (DefaultTableModel) orderItemTable.getModel();
            orderItemModel.setRowCount(0);
        }
    }

    public void updateOrderItemTable(List<OrderItem> orderItems) {
        DefaultTableModel model = (DefaultTableModel) orderItemTable.getModel();
        model.setRowCount(0); // Clear old data

        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            double thanhTien = product.getPrice() * item.getQuantity();

            Object[] rowData = {
                    product.getIdProduct(),
                    product.getName(),
                    product.getSize(),
                    item.getQuantity(),
                    currencyFormat.format(product.getPrice()),
                    currencyFormat.format(thanhTien),
                    product.getNote()
            };
            model.addRow(rowData);
        }
    }

    public void updateTotalRevenue(double totalRevenue) {
        totalRevenueTextArea.setText(currencyFormat.format(totalRevenue));
    }

    public JTable getCustomerTable() {
        return customerTable;
    }

    public JTable getOrderTable() {
        return orderTable;
    }
    }