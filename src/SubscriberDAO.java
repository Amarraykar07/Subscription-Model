import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDAO {

    public void insertSubscriber(Subscriber s) throws SQLException {
        String sql = "INSERT INTO subscriber (subscriber_id, name, email, address, phone, subscription_start_date, subscription_end_date, subscription_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getSubscriberId());
            stmt.setString(2, s.getName());
            stmt.setString(3, s.getEmail());
            stmt.setString(4, s.getAddress());
            stmt.setString(5, s.getPhone());
            stmt.setDate(6, Date.valueOf(s.getSubscriptionStartDate()));
            stmt.setDate(7, Date.valueOf(s.getSubscriptionEndDate()));
            stmt.setString(8, s.getSubscriptionStatus());
            stmt.executeUpdate();
            System.out.println("✅ Subscriber inserted successfully!");
        }
    }

    public Subscriber getSubscriberById(int id) throws SQLException {
        String sql = "SELECT * FROM subscriber WHERE subscriber_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Subscriber(
                    rs.getInt("subscriber_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getDate("subscription_start_date").toString(),
                    rs.getDate("subscription_end_date").toString(),
                    rs.getString("subscription_status")
                );
            }
        }
        return null;
    }

    public List<Subscriber> getAllSubscribers() throws SQLException {
        String sql = "SELECT * FROM subscriber";
        List<Subscriber> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Subscriber(
                    rs.getInt("subscriber_id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getDate("subscription_start_date").toString(),
                    rs.getDate("subscription_end_date").toString(),
                    rs.getString("subscription_status")
                ));
            }
        }
        return list;
    }

    public void updateSubscriber(Subscriber s) throws SQLException {
        String sql = "UPDATE subscriber SET name=?, email=?, address=?, phone=?, subscription_start_date=?, subscription_end_date=?, subscription_status=? WHERE subscriber_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, s.getName());
            stmt.setString(2, s.getEmail());
            stmt.setString(3, s.getAddress());
            stmt.setString(4, s.getPhone());
            stmt.setDate(5, Date.valueOf(s.getSubscriptionStartDate()));
            stmt.setDate(6, Date.valueOf(s.getSubscriptionEndDate()));
            stmt.setString(7, s.getSubscriptionStatus());
            stmt.setInt(8, s.getSubscriberId());
            stmt.executeUpdate();
            System.out.println("✅ Subscriber updated successfully!");
        }
    }

    public void deleteSubscriber(int id) throws SQLException {
        String sql = "DELETE FROM subscriber WHERE subscriber_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Subscriber deleted successfully!");
        }
    }
}
