import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public void insertOrder(Order o) throws SQLException {
        String sql = "INSERT INTO orders (order_id, subscriber_id, order_date, amount, status, payment_status, delivery_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getOrderId());
            stmt.setInt(2, o.getSubscriberId());
            stmt.setTimestamp(3, Timestamp.valueOf(o.getOrderDate()));
            stmt.setDouble(4, o.getAmount());
            stmt.setString(5, o.getStatus());
            stmt.setString(6, o.getPaymentStatus());

            if (o.getDeliveryDate() != null && !o.getDeliveryDate().isEmpty()) {
                stmt.setDate(7, Date.valueOf(o.getDeliveryDate()));
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.executeUpdate();
            System.out.println("✅ Order inserted successfully!");
        }
    }

    public List<Order> getOrdersBySubscriberId(int subscriberId) throws SQLException {
        String query = "SELECT * FROM orders WHERE subscriber_id = ?";
        List<Order> orders = new ArrayList<>();
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, subscriberId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int subId = rs.getInt("subscriber_id");
    
                // Handle nullable date fields safely
                Date orderDate = rs.getDate("order_date");
                String orderDateStr = (orderDate != null) ? orderDate.toString() : "N/A";
    
                double amount = rs.getDouble("amount");
                String status = rs.getString("status");
                String paymentStatus = rs.getString("payment_status");
    
                Date deliveryDate = rs.getDate("delivery_date");
                String deliveryDateStr = (deliveryDate != null) ? deliveryDate.toString() : "N/A";
    
                // Create the Order object
                Order order = new Order(
                    orderId,
                    subId,
                    orderDateStr,
                    amount,
                    status,
                    paymentStatus,
                    deliveryDateStr
                );
    
                orders.add(order);
            }
        }
        return orders;
    }    

    public Order getOrderById(int id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String delivery = rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toString() : null;

                return new Order(
                    rs.getInt("order_id"),
                    rs.getInt("subscriber_id"),
                    rs.getTimestamp("order_date").toString(),
                    rs.getDouble("amount"),
                    rs.getString("status"),
                    rs.getString("payment_status"),
                    delivery
                );
            }
        }
        return null;
    }

    public List<Order> getAllOrders() throws SQLException {
        String sql = "SELECT * FROM orders";
        List<Order> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String delivery = rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toString() : null;

                list.add(new Order(
                    rs.getInt("order_id"),
                    rs.getInt("subscriber_id"),
                    rs.getTimestamp("order_date").toString(),
                    rs.getDouble("amount"),
                    rs.getString("status"),
                    rs.getString("payment_status"),
                    delivery
                ));
            }
        }
        return list;
    }

    public void updateOrder(Order o) throws SQLException {
        String sql = "UPDATE orders SET subscriber_id=?, order_date=?, amount=?, status=?, payment_status=?, delivery_date=? WHERE order_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, o.getSubscriberId());
            stmt.setTimestamp(2, Timestamp.valueOf(o.getOrderDate()));
            stmt.setDouble(3, o.getAmount());
            stmt.setString(4, o.getStatus());
            stmt.setString(5, o.getPaymentStatus());

            if (o.getDeliveryDate() != null && !o.getDeliveryDate().isEmpty()) {
                stmt.setDate(6, Date.valueOf(o.getDeliveryDate()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.setInt(7, o.getOrderId());
            stmt.executeUpdate();
            System.out.println("✅ Order updated successfully!");
        }
    }

    public void deleteOrder(int id) throws SQLException {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Order deleted successfully!");
        }
    }
}
