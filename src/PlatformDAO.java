import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlatformDAO {

    public void insertPlatform(Platform platform) throws SQLException {
        String sql = "INSERT INTO platform (platform_id, platform_name, platform_url, platform_description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, platform.getPlatformId());
            stmt.setString(2, platform.getPlatformName());
            stmt.setString(3, platform.getPlatformUrl());
            stmt.setString(4, platform.getPlatformDescription());
            stmt.executeUpdate();
            System.out.println("✅ Platform inserted successfully!");
        }
    }

    public Platform getPlatformById(int id) throws SQLException {
        String sql = "SELECT * FROM platform WHERE platform_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Platform(
                    rs.getInt("platform_id"),
                    rs.getString("platform_name"),
                    rs.getString("platform_url"),
                    rs.getString("platform_description")
                );
            }
        }
        return null;
    }

    public List<Platform> getAllPlatforms() throws SQLException {
        String sql = "SELECT * FROM platform";
        List<Platform> platforms = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                platforms.add(new Platform(
                    rs.getInt("platform_id"),
                    rs.getString("platform_name"),
                    rs.getString("platform_url"),
                    rs.getString("platform_description")
                ));
            }
        }
        return platforms;
    }

    public void updatePlatform(Platform platform) throws SQLException {
        String sql = "UPDATE platform SET platform_name=?, platform_url=?, platform_description=? WHERE platform_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, platform.getPlatformName());
            stmt.setString(2, platform.getPlatformUrl());
            stmt.setString(3, platform.getPlatformDescription());
            stmt.setInt(4, platform.getPlatformId());
            stmt.executeUpdate();
            System.out.println("✅ Platform updated successfully!");
        }
    }

    public void deletePlatform(int id) throws SQLException {
        String sql = "DELETE FROM platform WHERE platform_id=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("🗑️ Platform deleted successfully!");
        }
    }
}
