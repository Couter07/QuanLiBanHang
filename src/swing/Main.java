package swing;

import swing.Store;
import swing.Customer;
import swing.Order;
import swing.OrderItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

public class Main extends JFrame {
    private Store store;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HighlandsCoffeeGUI_Panel1 panel1;
    private HighlandsCoffeeGUI_Panel2 panel2;
    private DecimalFormat currencyFormat = new DecimalFormat("#,###.##");

    public Main(Store store) {
        this.store = store;
        setTitle("Highlands Coffee Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create instances of the two panels
        panel1 = new HighlandsCoffeeGUI_Panel1(store, this);
        panel2 = new HighlandsCoffeeGUI_Panel2(store);

        // Add panels to the main panel with CardLayout
        mainPanel.add(panel1, "Panel1");
        mainPanel.add(panel2, "Panel2");

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Tính năng");

        JMenuItem panel1MenuItem = new JMenuItem("Tạo hoá đơn mới");
        panel1MenuItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "Panel1");
            panel1.resetPanel();
        });
        fileMenu.add(panel1MenuItem);

        JMenuItem panel2MenuItem = new JMenuItem("Xem danh sách");
        panel2MenuItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "Panel2");
            updateCustomerTable();
            if (store.getCustomers().size() > 0) {
                panel2.getCustomerTable().setRowSelectionInterval(0, 0);
                Customer firstCustomer = store.getCustomers().get(0);
                updateOrderTable(firstCustomer);
                if (store.getOrdersByCustomer(firstCustomer).size() > 0) {
                    panel2.getOrderTable().setRowSelectionInterval(0, 0);
                    String orderId = (String) panel2.getOrderTable().getValueAt(0, 0);
                    updateOrderItemTablePanel2(orderId);
                } else {
                    panel2.updateOrderItemTable(List.of());
                }

            } else {
                panel2.updateOrderTable(List.of());
                panel2.updateOrderItemTable(List.of());
            }
            updateRevenue();
        });
        fileMenu.add(panel2MenuItem);

        menuBar.add(fileMenu);

        // Exit Menu Item
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        menuBar.add(exitMenuItem);
        exitMenuItem.setPreferredSize(new Dimension(60, 20)); // Set size of the button

        setJMenuBar(menuBar);
        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "Panel1");

        // Initialize Data and Listeners for Panel 2
        updateCustomerTable();
        updateRevenue();

        panel2.addCustomerSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = panel2.getCustomerTable().getSelectedRow();
                if (selectedRow != -1) {
                    Customer selectedCustomer = store.getCustomers().get(selectedRow);
                    updateOrderTable(selectedCustomer);
                }
            }
        });

        panel2.addOrderSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = panel2.getOrderTable().getSelectedRow();
                if (selectedRow != -1) {
                    String orderId = (String) panel2.getOrderTable().getValueAt(selectedRow, 0);
                    updateOrderItemTablePanel2(orderId);
                }
            }
        });

        setVisible(true);
    }

    public void updateCustomerTable() {
        panel2.updateCustomerTable(store.getCustomers());
    }

    public void updateOrderTable(Customer customer) {
        panel2.updateOrderTable(store.getOrdersByCustomer(customer));
        // Clear order item details if no order is selected
        panel2.updateOrderItemTable(List.of());
    }

    public void updateOrderItemTablePanel2(String orderId) {
        Order order = store.getOrderById(orderId);
        if (order != null) {
            panel2.updateOrderItemTable(order.getItems());
        } else {
            panel2.updateOrderItemTable(List.of());
        }
    }

    public void updateRevenue() {
        double totalRevenue = 0;
        for (Order order : store.getOrders()) {
            totalRevenue += order.getTotalPrice();
        }
        panel2.updateTotalRevenue(totalRevenue);
    }

    public void selectLastAddedOrderPanel2() {
        cardLayout.show(mainPanel, "Panel2");
        List<Customer> customers = store.getCustomers();
        if (!customers.isEmpty()) {
            Customer lastCustomer = customers.get(customers.size() - 1);
            updateCustomerTable();
            // Try to select the last customer
            int lastCustomerIndex = customers.size() - 1;
            panel2.getCustomerTable().setRowSelectionInterval(lastCustomerIndex, lastCustomerIndex);
            updateOrderTable(lastCustomer);
            List<Order> orders = store.getOrdersByCustomer(lastCustomer);
            if (!orders.isEmpty()) {
                int lastOrderIndex = orders.size() - 1;
                panel2.getOrderTable().setRowSelectionInterval(lastOrderIndex, lastOrderIndex);
                String orderId = orders.get(lastOrderIndex).getIdOrder();
                updateOrderItemTablePanel2(orderId);
            } else {
                panel2.updateOrderItemTable(List.of());
            }
        } else {
            panel2.updateOrderTable(List.of());
            panel2.updateOrderItemTable(List.of());
        }
        updateRevenue();
    }

    // Static method for styling JTable headers (moved from original GUI)
    public static void styleTable(JTable table) {
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(173, 216, 230)); // Light blue
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        // Center-align column headers
        javax.swing.table.DefaultTableCellRenderer headerRenderer = (javax.swing.table.DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Set background color for the table cells
        javax.swing.table.DefaultTableCellRenderer cellRenderer = new javax.swing.table.DefaultTableCellRenderer();
        cellRenderer.setBackground(new Color(224, 242, 255)); // Softer blue
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Store store = new Store();
            new Main(store);
        });
    }
}