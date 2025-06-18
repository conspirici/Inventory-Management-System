package com.grocerflow.model.dao;

import com.grocerflow.model.User;
import com.grocerflow.util.DBConnection;
import com.grocerflow.util.PasswordHasher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean createUser(User user) {
        String sql = "INSERT INTO Users (username, password_hash, email, role, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getRole());
            stmt.setString(5, user.getStatus());

            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User validateLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND status = 'approved'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (PasswordHasher.verifyPassword(password, storedHash)) {
                    return mapResultSetToUser(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean approveUser(int userId) {
        return updateUserStatus(userId, "approved");
    }

    public boolean rejectUser(int userId) {
        return updateUserStatus(userId, "rejected");
    }

    public boolean updateUserStatus(int userId, String status) {
        String sql = "UPDATE Users SET status = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getPendingUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE status = 'pending'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getString("role"));
        user.setStatus(rs.getString("status"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
    
    public User getUserByUsername(String username) {
    String sql = "SELECT * FROM Users WHERE username = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return mapResultSetToUser(rs);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM Users";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            users.add(mapResultSetToUser(rs));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}
    public List<User> getUsersByStatus(String status) {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM Users WHERE status = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, status);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            users.add(mapResultSetToUser(rs));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return users;
}
    
    public boolean deleteUser(int userId) {
    String sql = "DELETE FROM Users WHERE user_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, userId);
        return stmt.executeUpdate() == 1;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public boolean updateUser(User user) {
    String sql = "UPDATE Users SET username = ?, password_hash = ?, email = ?, role = ?, status = ? WHERE user_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPasswordHash());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getRole());
        stmt.setString(5, user.getStatus());
        stmt.setInt(6, user.getUserId());

        return stmt.executeUpdate() == 1;

    } catch (SQLException e) {
        System.err.println("Error inserting user: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

}
