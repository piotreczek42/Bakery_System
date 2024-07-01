import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static final String URL = "jdbc:mysql://localhost:3306/bakerysystem";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public OrderRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addOrder(String customerName, String customerType, String product, int quantity) throws SQLException {
        String query = "INSERT INTO orders (customerName, customerType, product, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerName);
            stmt.setString(2, customerType);
            stmt.setString(3, product);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();
        }
    }

    public List<Order> loadOrders() throws SQLException {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getString("customerName"),
                        rs.getString("customerType"),
                        rs.getString("product"),
                        rs.getInt("quantity")
                ));
            }
        }
        return orders;
    }

    public void deleteOrder(int id) throws SQLException {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
