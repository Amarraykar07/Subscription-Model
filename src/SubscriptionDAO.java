import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {

    public void insertSubscription(Subscription s) throws SQLException {
        String sql = "INSERT INTO subscription (subscription_id, subscriber_id, platform_id, subscription_plan, start_date, end_date, subscription_status, renewal_auto) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getSubscriptionId());
            stmt.setInt(2, s.getSubscriberId());
            stmt.setInt(3, s.getPlatformId());
            stmt.setString(4, s.getSubscriptionPlan());
            stmt.setDate(5, Date.valueOf(s.getStartDate()));
            stmt.setDate(6, Date.valueOf(s.getEndDate()));
            stmt.setString(7, s.getSubscriptionStatus());
            stmt.setBoolean(8, s.isRenewalAuto());

            stmt.executeUpdate();
            System.out.println("✅ Subscription inserted successfully!");
        }
    }

    public Subscription getSubscriptionById(int id) throws SQLException {
        String sql = "SELECT * FROM subscription WHERE subscription_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Subscription(
                    rs.getInt("subscription_id"),
                    rs.getInt("subscriber_id"),
                    rs.getInt("platform_id"),
                    rs.getString("subscription_plan"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    rs.getString("subscription_status"),
                    rs.getBoolean("renewal_auto")
                );
            }
        }
        return null;
    }

    public List<Subscription> getSubscriptionsBySubscriberId(int subscriberId) throws SQLException {
        String query = "SELECT * FROM subscription WHERE subscriber_id = ?";
        List<Subscription> subscriptions = new ArrayList<>();
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
    
            stmt.setInt(1, subscriberId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Subscription subscription = new Subscription(
                    rs.getInt("subscription_id"),
                    rs.getInt("subscriber_id"),
                    rs.getInt("platform_id"),
                    rs.getString("subscription_plan"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    rs.getString("subscription_status"),
                    rs.getBoolean("renewal_auto")
                );
                subscriptions.add(subscription);
            }
        }
        return subscriptions;
    }    

    public List<Subscription> getAllSubscriptions() throws SQLException {
        String sql = "SELECT * FROM subscription";
        List<Subscription> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Subscription(
                    rs.getInt("subscription_id"),
                    rs.getInt("subscriber_id"),
                    rs.getInt("platform_id"),
                    rs.getString("subscription_plan"),
                    rs.getDate("start_date").toString(),
                    rs.getDate("end_date").toString(),
                    rs.getString("subscription_status"),
                    rs.getBoolean("renewal_auto")
                ));
            }
        }
        return list;
    }

    public void updateSubscription(Subscription s) throws SQLException {
        String sql = "UPDATE subscription SET subscriber_id=?, platform_id=?, subscription_plan=?, start_date=?, end_date=?, subscription_status=?, renewal_auto=? WHERE subscription_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, s.getSubscriberId());
            stmt.setInt(2, s.getPlatformId());
            stmt.setString(3, s.getSubscriptionPlan());
            stmt.setDate(4, Date.valueOf(s.getStartDate()));
            stmt.setDate(5, Date.valueOf(s.getEndDate()));
            stmt.setString(6, s.getSubscriptionStatus());
            stmt.setBoolean(7, s.isRenewalAuto());
            stmt.setInt(8, s.getSubscriptionId());

            stmt.executeUpdate();
            System.out.println("✅ Subscription updated successfully!");
        }
    }

    public void deleteSubscription(int id) throws SQLException {
        String sql = "DELETE FROM subscription WHERE subscription_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Subscription deleted successfully!");
        }
    }
}
