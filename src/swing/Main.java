package swing;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Store store;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HighlandsCoffeeGUI_Panel1 panel1;
    private HighlandsCoffeeGUI_Panel2 panel2;

    public Main() {
        store = new Store();

        setTitle("Highlands Coffee Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels for the two interfaces
        panel1 = new HighlandsCoffeeGUI_Panel1(store);
        panel2 = new HighlandsCoffeeGUI_Panel2(store);

        // Set store for Panel1
        panel1.setStore(store);

        // Set Store for Panel 2
        panel2.setStore(store);

        // Add panels to the main panel with CardLayout
        mainPanel.add(panel1, "Panel1");
        mainPanel.add(panel2, "Panel2");

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Tính năng");

        JMenuItem panel1MenuItem = new JMenuItem("Tạo hoá đơn mới");
        panel1MenuItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "Panel1");
            // Generate and set order ID and date when switching to Panel1
            panel1.generateAndSetOrderId();
            panel1.setDefaultDate();
        });
        fileMenu.add(panel1MenuItem);

        JMenuItem panel2MenuItem = new JMenuItem("Xem danh sách");
        panel2MenuItem.addActionListener(e -> {
            cardLayout.show(mainPanel, "Panel2");
            panel2.updateCustomerTable();
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

        panel1.generateAndSetOrderId();
        panel1.setDefaultDate();

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}
