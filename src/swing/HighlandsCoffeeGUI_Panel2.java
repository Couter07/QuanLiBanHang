package swing;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HighlandsCoffeeGUI_Panel2 extends JPanel {

    private Store store;

    private JTable customerTable;
    private JTable orderTable;
    private JTable orderItemTablePanel2;
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

        // Init customer
        customerTable.setRowSelectionAllowed(true);

        // Customer selected will trigger change on Order List
        customerTable.getSelectionModel().addListSelectionListener(this::onCustomerSelected);

        // Add listener to orderTable for order detail
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = orderTable.getSelectedRow();
                if (selectedRow != -1) {
                    String orderId = (String) orderTable.getValueAt(selectedRow, 0);
                    updateOrderItemTablePanel2(orderId);
                }
            }
        });
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
        styleTable(customerTable); // Apply style to customerTable
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
        styleTable(orderTable); // Apply style to orderTable
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
        orderItemTablePanel2 = new JTable(model);
        styleTable(orderItemTablePanel2); // Apply style to orderItemTablePanel2
        JScrollPane scrollPane = new JScrollPane(orderItemTablePanel2);
        panel.add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(new Dimension(600, 275));

        return panel;
    }

    // Method to style the JTable headers
    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(173, 216, 230)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        // Center-align column headers
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set background color for the table cells
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(new Color(224, 242, 255)); // Softer blue
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
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


    private void onCustomerSelected(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer selectedCustomer = store.getCustomers().get(selectedRow);
                updateOrderTable(selectedCustomer);
            }
        }
    }

    public void selectLastAddedOrder() {
        if (orderTable != null && orderTable.getRowCount() > 0) {
            int lastRowIndex = orderTable.getRowCount() - 1;
            orderTable.setRowSelectionInterval(lastRowIndex, lastRowIndex);
            String orderId = (String) orderTable.getValueAt(lastRowIndex, 0);
            updateOrderItemTablePanel2(orderId);
        }
    }

    public void updateOrderItemTablePanel2(String orderId) {
        Order order = store.getOrderById(orderId);
        DefaultTableModel model = (DefaultTableModel) orderItemTablePanel2.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ

        if (order != null) {
            for (OrderItem item : order.getItems()) {
                Product product = item.getProduct();
                double thanhTien = product.getPrice() * item.getQuantity();  // calculate thành tiền

                Object[] rowData = {
                        product.getIdProduct(),
                        product.getName(),
                        product.getSize(),
                        item.getQuantity(),
                        currencyFormat.format(product.getPrice()),
                        currencyFormat.format(thanhTien),  // Add to rowData
                        product.getNote()
                };
                model.addRow(rowData);
            }
        }
    }

    public void updateCustomerTable() {
        List<Customer> customers = store.getCustomers();
        Object[][] data = new Object[customers.size()][2];

        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getName();
            data[i][1] = customer.getNumberPhone();
        }

        String[] columnNames = {"Tên", "Số điện thoại"};
        DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
        model.setDataVector(data, columnNames);

        if(customers.size() > 0){
            customerTable.setRowSelectionInterval(0,0);
            Customer firstCustomer = store.getCustomers().get(0);
            updateOrderTable(firstCustomer);
            if(store.getOrdersByCustomer(firstCustomer).size() > 0){
                orderTable.setRowSelectionInterval(0,0);
                String orderId = (String) orderTable.getValueAt(0, 0);
                updateOrderItemTablePanel2(orderId);
            }

        }
    }

    public void updateOrderTable(Customer customer) {
        List<Order> orders = store.getOrdersByCustomer(customer);
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
        // Select the first row if orders are available
        if (orders.size() > 0) {
            orderTable.setRowSelectionInterval(0, 0);
            String orderId = (String) orderTable.getValueAt(0, 0);
            updateOrderItemTablePanel2(orderId);
        } else {
            // Clear the order item table if there are no orders for the customer
            DefaultTableModel orderItemModel = (DefaultTableModel) orderItemTablePanel2.getModel();
            orderItemModel.setRowCount(0);
        }
    }


    public void updateRevenue() {
        double totalRevenue = 0;
        for (Order order : store.getOrders()) {
            totalRevenue += order.getTotalPrice();
        }
        totalRevenueTextArea.setText(currencyFormat.format(totalRevenue));
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }
}
