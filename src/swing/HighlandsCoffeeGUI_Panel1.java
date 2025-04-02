package swing;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HighlandsCoffeeGUI_Panel1 extends JPanel {

    private Store store;

    // Input Fields for New Order (GUI 1)
    private JTextField orderIdField;
    private JTextField orderDateField;
    private JTextField customerNameField;
    private JTextField customerPhoneField;
    private JTextField customerAddressField;
    private JTextField customerLoyaltyPointsField;
    private JTextField promotionField;
    private JTextField totalField;

    // Order Item Table Model for GUI 1
    private DefaultTableModel orderItemTableModel;
    private JTable orderItemTable;

    // Product Data
    private Map<String, Double> productPrices;
    private String[] productNames = {"Pancake", "CheeseCake", "MilkTea", "MatchaLatte"};
    private String[] productSizes = {"S", "M", "L"};
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.##");

    public HighlandsCoffeeGUI_Panel1(Store store) {
        this.store = store;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize product data
        productPrices = new HashMap<>();
        productPrices.put("Pancake", 50000.0);
        productPrices.put("CheeseCake", 60000.0);
        productPrices.put("MilkTea", 30000.0);
        productPrices.put("MatchaLatte", 70000.0);

        // Left Panel: Input Form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        JPanel inputPanel = createNewOrderInputPanel();
        leftPanel.add(inputPanel);

        // Center Panel: Order Item Details
        JPanel orderItemPanel = createOrderItemPanelForInput();

        // South Panel (Total and Promotion)
        JPanel totalPricePanel = createTotalPricePanel();

        // Add panels to the main panel
        add(leftPanel, BorderLayout.WEST);
        add(orderItemPanel, BorderLayout.CENTER);
        add(totalPricePanel, BorderLayout.SOUTH);
    }

    public void setDefaultDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        orderDateField.setText(dateFormat.format(new Date()));
    }

    public void generateAndSetOrderId() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000000);
        String newOrderId =  "HighlandsCoffee@" + randomNumber;
        orderIdField.setText(newOrderId);
    }

    private JPanel createTotalPricePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel totalLabel = new JLabel("Tổng:");
        totalField = new JTextField(10);
        totalField.setEditable(false);

        JLabel promotionLabel = new JLabel("Khuyến mãi (%):");
        promotionField = new JTextField(10);
        promotionField = new JTextField(10);
        promotionField.setText("");

        panel.add(totalLabel);
        panel.add(totalField);
        panel.add(promotionLabel);
        panel.add(promotionField);

        // ActionListener for promotionField to recalculate the total
        promotionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                calculateAndDisplayTotal();
            }
        });

        return panel;
    }

    private JPanel createNewOrderInputPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        panel.setPreferredSize(new Dimension(300, 200));

        panel.add(new JLabel("ID Hóa đơn:"));
        orderIdField = new JTextField();
        orderIdField.setEditable(false);
        panel.add(orderIdField);

        panel.add(new JLabel("Ngày (dd/MM/yyyy):"));
        orderDateField = new JTextField();
        orderDateField.setEditable(false);
        panel.add(orderDateField);

        panel.add(new JLabel("Tên:"));
        customerNameField = new JTextField();
        panel.add(customerNameField);

        panel.add(new JLabel("Số điện thoại:"));
        customerPhoneField = new JTextField();
        panel.add(customerPhoneField);

        panel.add(new JLabel("Địa chỉ:"));
        customerAddressField = new JTextField();
        panel.add(customerAddressField);

        panel.add(new JLabel("Điểm tích lũy:"));
        customerLoyaltyPointsField = new JTextField();
        panel.add(customerLoyaltyPointsField);

        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(e -> saveNewOrder());
        panel.add(saveButton);

        return panel;
    }

    private JPanel createOrderItemPanelForInput() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Chi tiết hóa đơn"));

        String[] columnNames = {"ID Sản phẩm", "Tên", "Kích cỡ", "Số lượng", "Giá", "Thành tiền", "Ghi chú"};
        orderItemTableModel = new DefaultTableModel(columnNames, 0);
        orderItemTable = new JTable(orderItemTableModel);
        styleTable(orderItemTable); // Apply style to orderItemTable
        orderItemTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        // Add an empty row initially
        Object[] newRow = {"", "", "", "", "", "", ""};
        orderItemTableModel.addRow(newRow);

        // Set up product name selection with JComboBox
        TableColumn productNameColumn = orderItemTable.getColumnModel().getColumn(1);
        JComboBox<String> productNameComboBox = new JComboBox<>(productNames);
        productNameColumn.setCellEditor(new DefaultCellEditor(productNameComboBox));

        // Set up size selection with JComboBox
        TableColumn productSizeColumn = orderItemTable.getColumnModel().getColumn(2);
        JComboBox<String> productSizeComboBox = new JComboBox<>(productSizes);
        productSizeColumn.setCellEditor(new DefaultCellEditor(productSizeComboBox));

        // Add ActionListener to the combo box for size and price
        productSizeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderItemTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedSize = (String) productSizeComboBox.getSelectedItem();
                    String productId = (String) orderItemTableModel.getValueAt(selectedRow, 0);
                    double basePrice = 0;
                    String productName = (String) orderItemTableModel.getValueAt(selectedRow, 1);

                    if (productName != null && !productName.isEmpty()) {
                        basePrice = productPrices.get(productName);
                    } else {
                        return; // Exit if product name is not set
                    }

                    double multiplier = 1.0;
                    switch (selectedSize) {
                        case "L":
                            multiplier = 1.1;
                            break;
                        case "M":
                            multiplier = 1.4;
                            break;
                        default:
                            multiplier = 1.0; // "S" size
                    }
                    double newPrice = basePrice * multiplier;
                    orderItemTableModel.setValueAt(currencyFormat.format(newPrice), selectedRow, 4);
                    calculateThanhTien(selectedRow);
                    calculateAndDisplayTotal();
                }
            }
        });

        // Add ActionListener to the combo box
        productNameComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = orderItemTable.getSelectedRow();
                if (selectedRow != -1) {
                    String selectedProductName = (String) productNameComboBox.getSelectedItem();
                    // Generate product ID
                    String productId = generateProductId(selectedProductName);
                    // Set the ID and price in the table
                    orderItemTableModel.setValueAt(productId, selectedRow, 0);
                    orderItemTableModel.setValueAt(currencyFormat.format(productPrices.get(selectedProductName)), selectedRow, 4);
                    // Calculate new price based on size (default to "S" size)
                    String selectedSize = (String) productSizeComboBox.getSelectedItem();
                    if(selectedSize == null) selectedSize = "S";

                    double basePrice = productPrices.get(selectedProductName);
                    double multiplier = 1.0;
                    switch (selectedSize) {
                        case "L":
                            multiplier = 1.1;
                            break;
                        case "M":
                            multiplier = 1.4;
                            break;
                        default:
                            multiplier = 1.0; // "S" size
                    }
                    double newPrice = basePrice * multiplier;
                    orderItemTableModel.setValueAt(currencyFormat.format(newPrice), selectedRow, 4);
                    calculateThanhTien(selectedRow);
                    calculateAndDisplayTotal();
                }
            }
        });

        // Key listener for automatic row movement
        orderItemTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int row = orderItemTable.getSelectedRow();
                int col = orderItemTable.getSelectedColumn();

                if (keyCode == KeyEvent.VK_ENTER) {
                    if (row < orderItemTableModel.getRowCount() - 1) {
                        orderItemTable.changeSelection(row + 1, col, false, false);
                    } else {
                        Object[] newRow = {"", "", "", "", "", "", ""};
                        orderItemTableModel.addRow(newRow);
                        orderItemTable.changeSelection(row + 1, col, false, false);
                    }
                    e.consume();
                }
            }
        });

        // Add TableModelListener to recalculate thành tiền
        orderItemTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    int column = e.getColumn();

                    if (row >= 0 && (column == 0 || column == 3 || column == 4)) {
                        calculateThanhTien(row);
                    }
                    calculateAndDisplayTotal();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(orderItemTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Method to generate a product ID
    private String generateProductId(String productName) {
        // Implement logic to generate a unique product ID (e.g., using a counter or random number)
        Random random = new Random();
        int randomNumber = random.nextInt(100000);
        return productName.substring(0, 3).toUpperCase() + randomNumber;
    }


    private void calculateThanhTien(int row) {
        if (row < 0) return;
        try {
            String productId = (String) orderItemTableModel.getValueAt(row, 0);
            String quantityStr = (String) orderItemTableModel.getValueAt(row, 3);
            String priceStr = (String) orderItemTableModel.getValueAt(row, 4);

            int quantity = (quantityStr != null && !quantityStr.isEmpty()) ? Integer.parseInt(quantityStr) : 0;

            Product product = store.getProductById(productId);
            double price = (product != null) ? product.getPrice() : (priceStr != null && !priceStr.isEmpty()) ? currencyFormat.parse(priceStr).doubleValue() : 0;

            double thanhTien = price * quantity;
            orderItemTableModel.setValueAt(currencyFormat.format(thanhTien), row, 5);
        } catch (Exception ex) {
            orderItemTableModel.setValueAt("0", row, 5);
        }
    }

    private void calculateAndDisplayTotal() {
        double total = 0.0;
        for (int i = 0; i < orderItemTableModel.getRowCount(); i++) {
            try {
                String thanhTienStr = (String) orderItemTableModel.getValueAt(i, 5);
                if (thanhTienStr != null && !thanhTienStr.isEmpty()) {
                    total += currencyFormat.parse(thanhTienStr).doubleValue();
                }
            } catch (Exception ignored) {}
        }

        double discount = 0.0;
        try {
            discount = Double.parseDouble(promotionField.getText());
        } catch (NumberFormatException ignored) {
            promotionField.setText("");
        }

        double finalTotal = total * (1 - discount / 100);
        totalField.setText(currencyFormat.format(finalTotal));
    }

    private void saveNewOrder() {
        try {
            String orderId = orderIdField.getText();
            Date orderDate = new SimpleDateFormat("dd/MM/yyyy").parse(orderDateField.getText());
            String customerName = customerNameField.getText();
            String customerPhone = customerPhoneField.getText();
            String customerAddress = customerAddressField.getText();
            int loyaltyPoints = Integer.parseInt(customerLoyaltyPointsField.getText());
            double promotion = Double.parseDouble(promotionField.getText());

            Customer customer = store.getCustomerByPhone(customerPhone);
            if (customer == null) {
                customer = new Customer(customerName, customerPhone, customerAddress, loyaltyPoints);
                store.addCustomer(customer);
            }

            double totalPrice = currencyFormat.parse(totalField.getText()).doubleValue();

            Order order = new Order(orderId, orderDate, customer, promotion);
            for (int i = 0; i < orderItemTableModel.getRowCount(); i++) {
                String productId = (String) orderItemTableModel.getValueAt(i, 0);
                String quantityStr = (String) orderItemTableModel.getValueAt(i, 3);
                String productSize = (String) orderItemTableModel.getValueAt(i, 2);
                String note = (String) orderItemTableModel.getValueAt(i, 6);
                String priceStr = (String) orderItemTableModel.getValueAt(i, 4);
                String productName = (String) orderItemTableModel.getValueAt(i, 1);

                Product product = store.getProductById(productId);

                if (product == null) {
                    double price = 0;
                    try {
                        price = currencyFormat.parse(priceStr).doubleValue();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog((Component) this, "Invalid price for product " + productName, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    product = new Product(productId, productName, productSize, price, note);  // create a new product
                    store.addProduct(product);  // add the product to the store
                }

                int qty;

                try {
                    qty = Integer.parseInt(quantityStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog((Component) this, "Số lượng không hợp lệ cho sản phẩm " + product.getName(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (product != null) {
                    OrderItem orderItem = new OrderItem(product, qty);
                    order.addItem(orderItem);
                }
            }
            order.setTotalPrice(totalPrice);
            store.addOrder(order);

            // Cập nhật bảng hóa đơn và chi tiết hóa đơn ở giao diện 2
            ((HighlandsCoffeeGUI_Panel2)getParent()).updateCustomerTable();

            Customer currentCustomer = store.getCustomerByPhone(customerPhone);

            ((HighlandsCoffeeGUI_Panel2)getParent()).updateOrderTable(currentCustomer);  // Cập nhật bảng hóa đơn
            ((HighlandsCoffeeGUI_Panel2)getParent()).updateRevenue(); // Cập nhật doanh thu

            clearInputFields();
            generateAndSetOrderId();
            setDefaultDate();
            ((HighlandsCoffeeGUI_Panel2)getParent()).selectLastAddedOrder(); // Chọn hóa đơn mới và cập nhật giao diện 2

        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component) this, "Error saving order: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clearInputFields() {
        customerNameField.setText("");
        customerPhoneField.setText("");
        customerAddressField.setText("");
        customerLoyaltyPointsField.setText("");
        promotionField.setText("");
        totalField.setText("");
        orderItemTableModel.setRowCount(0);
        orderItemTableModel.addRow(new Object[]{"", "", "", "", "", "", ""});
    }

    // Method to style the JTable headers
    private void styleTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }
}
