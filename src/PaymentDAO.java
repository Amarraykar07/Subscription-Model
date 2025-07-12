import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    public void insertPayment(Payment p) throws SQLException {
        String sql = "INSERT INTO payment (payment_id, order_id, payment_date, payment_status, payment_method, payment_amount) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPaymentId());
            stmt.setInt(2, p.getOrderId());
            stmt.setTimestamp(3, Timestamp.valueOf(p.getPaymentDate()));
            stmt.setString(4, p.getPaymentStatus());
            stmt.setString(5, p.getPaymentMethod());
            stmt.setDouble(6, p.getPaymentAmount());

            stmt.executeUpdate();
            System.out.println("💰 Payment inserted successfully!");
        }
    }

    public List<Payment> getPaymentsBySubscriberId(int subscriberId) throws SQLException {
        String query = """
            SELECT p.* FROM payment p
            JOIN orders o ON p.order_id = o.order_id
            WHERE o.subscriber_id = ?
        """;
    
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, subscriberId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Payment payment = new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getTimestamp("payment_date").toString(),
                    rs.getString("payment_status"),
                    rs.getString("payment_method"),
                    rs.getDouble("payment_amount")
                );
                payments.add(payment);
            }
        }
        return payments;
    }    

    public Payment getPaymentById(int id) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getTimestamp("payment_date").toString(),
                    rs.getString("payment_status"),
                    rs.getString("payment_method"),
                    rs.getDouble("payment_amount")
                );
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() throws SQLException {
        String sql = "SELECT * FROM payment";
        List<Payment> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Payment(
                    rs.getInt("payment_id"),
                    rs.getInt("order_id"),
                    rs.getTimestamp("payment_date").toString(),
                    rs.getString("payment_status"),
                    rs.getString("payment_method"),
                    rs.getDouble("payment_amount")
                ));
            }
        }
        return list;
    }

    public void updatePayment(Payment p) throws SQLException {
        String sql = "UPDATE payment SET order_id=?, payment_date=?, payment_status=?, payment_method=?, payment_amount=? WHERE payment_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getOrderId());
            stmt.setTimestamp(2, Timestamp.valueOf(p.getPaymentDate()));
            stmt.setString(3, p.getPaymentStatus());
            stmt.setString(4, p.getPaymentMethod());
            stmt.setDouble(5, p.getPaymentAmount());
            stmt.setInt(6, p.getPaymentId());

            stmt.executeUpdate();
            System.out.println("💳 Payment updated successfully!");
        }
    }

    public void deletePayment(int id) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Payment deleted successfully!");
        }
    }
}
