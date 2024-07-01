import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class BakerySystem extends JFrame {
    private JTextField customerName;
    private JComboBox<String> customerType;
    private JComboBox<String> product;
    private JSpinner quantity;
    private JTable infoTable;
    private JPanel admin;
    private JButton addOrderButton;
    private JButton logoutButton;
    private JButton createIngredientsList;
    private JButton generateRaport;
    private JButton delButton;

    private OrderRepository orderRepository;

    BakerySystem() {
        setTitle("Bakery System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(this.admin);
        this.setSize(800, 600);
        setLocationRelativeTo(null);

        orderRepository = new OrderRepository();

        customerType.addItem("Individual");
        customerType.addItem("Company");
        product.addItem("Bread");
        product.addItem("Cake");
        product.addItem("Cookies");

        quantity.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        createIngredientsList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showIngredientsList();
            }
        });

        generateRaport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });

        loadOrders();
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addOrder() {
        String name = customerName.getText();
        String type = (String) customerType.getSelectedItem();
        String prod = (String) product.getSelectedItem();
        int qty = (int) quantity.getValue();

        if (name.isEmpty() || type == null || prod == null || qty <= 0) {
            JOptionPane.showMessageDialog(this, "Please fill all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            orderRepository.addOrder(name, type, prod, qty);
            loadOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadOrders() {
        try {
            List<Order> orders = orderRepository.loadOrders();
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Customer Name", "Customer Type", "Product", "Quantity"}, 0);
            for (Order order : orders) {
                model.addRow(new Object[]{order.getId(), order.getCustomerName(), order.getCustomerType(), order.getProduct(), order.getQuantity()});
            }
            infoTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showIngredientsList() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order from the table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        String prod = (String) model.getValueAt(selectedRow, 3);
        int qty = (int) model.getValueAt(selectedRow, 4);

        String ingredientsList = IngredientsCalculator.getIngredientsList(prod, qty);
        JOptionPane.showMessageDialog(this, ingredientsList, "Ingredients List", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateReport() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order from the table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        int id = (int) model.getValueAt(selectedRow, 0);
        String name = (String) model.getValueAt(selectedRow, 1);
        String type = (String) model.getValueAt(selectedRow, 2);
        String prod = (String) model.getValueAt(selectedRow, 3);
        int qty = (int) model.getValueAt(selectedRow, 4);

        try {
            ReportGenerator.generateReport(id, name, prod, qty, type);
            JOptionPane.showMessageDialog(this, "Facture generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error generating facture.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteOrder() {
        int selectedRow = infoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an order to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        int id = (int) model.getValueAt(selectedRow, 0);

        try {
            orderRepository.deleteOrder(id);
            loadOrders();
            JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BakerySystem().setVisible(true);
            }
        });
    }
}
