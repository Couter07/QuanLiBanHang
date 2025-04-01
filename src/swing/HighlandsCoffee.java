package swing;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HighlandsCoffee extends JFrame {
    private JTextField txtOrderId, txtDate, txtName, txtPhone, txtAddress, txtPoints, txtTotal, txtDiscount,txtTotalEmpty;
    private JTable table;
    private JMenuBar menuBar;
    private JMenuItem quanli, order;
    private JPanel leftPanel, customerPanel, orderPanel;
    private DefaultTableModel tableModel;
    private Menu menu; // Dùng class Menu



    public HighlandsCoffee() {
        setTitle("Highlands Coffee Management"); // Tiêu đề cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng chương trình khi tắt cửa sổ
        setSize(800, 500);
        setLayout(new BorderLayout()); // Thiết lập layout chính
        /// Khởi tạo menu món ăn
        menu = new Menu();

        // tinh nang exit
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFeature = new JMenu("Tính năng");
        JMenuItem quanli = new JMenuItem("Quản lí Khách hàng");
        JMenuItem order = new JMenuItem("Order");
        menuFeature.add(quanli);
        menuFeature.add(order);

        JButton btnExit = new JButton("Exit");
        btnExit.setBorder(null);
        btnExit.setContentAreaFilled(false);
        btnExit.addActionListener(e -> System.exit(0));
        menuBar.add(menuFeature);
        menuBar.add(btnExit);

        add(menuBar, BorderLayout.NORTH);

        // Panel bên trái chứa thông tin hóa đơn và khách hàng
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Panel thông tin hóa đơn
        JPanel orderPanel = new JPanel(new GridBagLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn")); // Viền tiêu đề
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // ID hóa đơn
        gbc.gridx = 0;
        gbc.gridy = 0;
        orderPanel.add(new JLabel("ID Hóa đơn:"), gbc);
        gbc.gridx = 1;
        txtOrderId = new JTextField(15);
        txtOrderId.setText(getIdOrder());
        orderPanel.add(txtOrderId, gbc);

        // Ngày
        gbc.gridx = 0;
        gbc.gridy = 1;
        orderPanel.add(new JLabel("Ngày:"), gbc);
        gbc.gridx = 1;
        txtDate = new JTextField(15);
        txtDate.setText(getCurrentDate());
        orderPanel.add(txtDate, gbc);

        // Panel thông tin khách hàng (đặt dưới panel hóa đơn)
        JPanel customerPanel = new JPanel(new GridBagLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng")); // Viền tiêu đề

        // Tên khách hàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        customerPanel.add(new JLabel("Tên:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(15);
        customerPanel.add(txtName, gbc);

        // Số điện thoại
        gbc.gridx = 0;
        gbc.gridy = 1;
        customerPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(15);
        customerPanel.add(txtPhone, gbc);

        // Địa chỉ
        gbc.gridx = 0;
        gbc.gridy = 2;
        customerPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1;
        txtAddress = new JTextField(15);
        customerPanel.add(txtAddress, gbc);

        // Điểm tích lũy
        gbc.gridx = 0;
        gbc.gridy = 3;
        customerPanel.add(new JLabel("Điểm tích lũy:"), gbc);
        gbc.gridx = 1;
        txtPoints = new JTextField(15);
        customerPanel.add(txtPoints, gbc);

        // Panel chứa nút Lưu
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("Lưu");
        buttonPanel.add(btnSave);
        btnSave.addActionListener(e -> checkout());

        // Thêm các panel vào panel bên trái
        leftPanel.add(orderPanel);
        leftPanel.add(customerPanel);
        leftPanel.add(buttonPanel);

        add(leftPanel, BorderLayout.WEST); // Đặt panel bên trái

        // Bảng chi tiết hóa đơn
        String[] columns = {"ID", "Tên", "Kích cỡ", "Số lượng", "Giá", "Thành tiền", "Chú thích"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);

        Object[] emptyRow = {"", "", "", "", "", "", ""};
        tableModel.addRow(emptyRow);

        // Xử lý sự kiện nhập ID món ăn
        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();

                    if (column == 0) { // Cột ID món ăn
                        String id = (String) tableModel.getValueAt(row, 0);
                        Menu.Food food = menu.getFoodById(id);

                        if (food != null) {
                            tableModel.setValueAt(food.name, row, 1); // Tên món ăn
                            tableModel.setValueAt(food.description, row, 6); // Chú thích
                        }
                    }
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);

        customerPanel.add(new JLabel("Điểm tích lũy:"), gbc);
        gbc.gridx = 1;
        txtPoints = new JTextField(15);
        customerPanel.add(txtPoints, gbc);

        // JScrollPane tableScrollPane = new JScrollPane(table);
        // add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        bottomPanel.add(new JLabel("Tổng:"));
        txtTotalEmpty = new JTextField(10);
        txtTotalEmpty.setBackground(table.getBackground()); // Ô trống thêm vào
        txtTotalEmpty.setBackground(table.getBackground()); // Ô trống thêm vào
        txtTotalEmpty.setEditable(false);

        txtTotal = new JTextField(10);
        txtTotal.setBackground(table.getBackground()); // Cài đặt màu nền giống bảng
        txtTotal.setEditable(false); // Chỉ hiển thị, không chỉnh sửa
        bottomPanel.add(txtTotal);
        bottomPanel.add(txtTotalEmpty);

        bottomPanel.add(new JLabel("Khuyến mãi:"));
        txtDiscount = new JTextField(20);
        txtDiscount.setBackground(table.getBackground()); // Cài đặt màu nền giống bảng
        txtDiscount.setEditable(false); // Chỉ hiển thị, không chỉnh sửa
        bottomPanel.add(txtDiscount);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void checkout() {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thêm thông tin! ");
        }
    }

    private static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(new Date());
    }

    private static String getIdOrder() {
        return "Highlands" + getCurrentDate()+ getCurrentTime();
    }

    private static String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return LocalTime.now().format(formatter);

    }
    static class Food {
        String name, description;
        Food(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }
}
