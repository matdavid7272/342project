package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.*;
import org.springframework.dao.DuplicateKeyException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Returns true if count is greater than 0
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User save(User user) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, age, dtype) VALUES (?, ?, ?, ?, ?)";
        String dtype = "user"; // Default value

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFirstname());
            stmt.setString(2, user.getLastname());
            stmt.setString(3, user.getEmail());
            stmt.setInt(4, user.getAge());
            stmt.setString(5, dtype);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return user;
    }

    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // We can add the dtype value here, but it won't be stored in the User class
                String dtype = rs.getString("dtype");

                // Return User without adding dtype as a field
                return new User(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getInt("age")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return null; // Return null if no user is found
    }

    public boolean deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Return true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return false; // Return false if deletion was unsuccessful
    }

    public User editUser(Long id, User updatedUser) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email_id = ?, age = ?, dtype = ? WHERE id = ?";
        String dtype = "user"; // Default value

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedUser.getFirstname());
            stmt.setString(2, updatedUser.getLastname());
            stmt.setString(3, updatedUser.getEmail());
            stmt.setInt(4, updatedUser.getAge());
            stmt.setString(5, dtype);
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedUser.setId(id);
                return updatedUser; // Return the updated user if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String dtype = rs.getString("dtype");

                User user;
                switch (dtype) {
                    case "client":
                        user = new Client();
                        break;
                    case "instructor":
                        // Assuming Instructor has a `specialization` attribute
                        String specialization = rs.getString("specialization");  // Adjust if stored separately
                        user = new Instructor(specialization);
                        break;
                    case "guardian":
                        user = new Guardian();
                        break;
                    case "administrator":
                        user = new Administrator();
                        break;
                    default:
                        user = new User();  // Default to User if dtype is unrecognized
                        break;
                }

                user.setId(rs.getLong("id"));
                user.setFirstname(rs.getString("first_name"));
                user.setLastname(rs.getString("last_name"));
                user.setEmail(rs.getString("email_id"));
                user.setAge(rs.getInt("age"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return users;
    }

}
