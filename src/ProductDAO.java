import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public void insertProduct(Product p) throws SQLException {
        String sql = "INSERT INTO product (product_id, platform_id, name, description, price, category, subscription_plan_applicable) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getProductId());
            stmt.setInt(2, p.getPlatformId());
            stmt.setString(3, p.getName());
            stmt.setString(4, p.getDescription());
            stmt.setDouble(5, p.getPrice());
            stmt.setString(6, p.getCategory());
            stmt.setBoolean(7, p.isSubscriptionPlanApplicable());

            stmt.executeUpdate();
            System.out.println("📦 Product inserted successfully!");
        }
    }

    public Product getProductById(int id) throws SQLException {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                    rs.getInt("product_id"),
                    rs.getInt("platform_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getBoolean("subscription_plan_applicable")
                );
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        String sql = "SELECT * FROM product";
        List<Product> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("product_id"),
                    rs.getInt("platform_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getString("category"),
                    rs.getBoolean("subscription_plan_applicable")
                ));
            }
        }
        return list;
    }

    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE product SET platform_id=?, name=?, description=?, price=?, category=?, subscription_plan_applicable=? WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, p.getPlatformId());
            stmt.setString(2, p.getName());
            stmt.setString(3, p.getDescription());
            stmt.setDouble(4, p.getPrice());
            stmt.setString(5, p.getCategory());
            stmt.setBoolean(6, p.isSubscriptionPlanApplicable());
            stmt.setInt(7, p.getProductId());

            stmt.executeUpdate();
            System.out.println("🛠️ Product updated successfully!");
        }
    }

    public void deleteProduct(int id) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Product deleted successfully!");
        }
    }
}
