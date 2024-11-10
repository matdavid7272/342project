package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Guardian;
import org.springframework.dao.DuplicateKeyException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GuardianRepository {

    private final DataSource dataSource;

    public GuardianRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Check if email exists in the database for uniqueness
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

    // Save a guardian to the database
    public Guardian save(Guardian guardian) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, age, dtype) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, guardian.getFirstname());
            stmt.setString(2, guardian.getLastname());
            stmt.setString(3, guardian.getEmail());
            stmt.setInt(4, guardian.getAge());
            stmt.setString(5, "guardian");  // Specifying dtype as "guardian"

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    guardian.setId(generatedKeys.getLong(1));  // Get the generated ID and set it
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return guardian;
    }

    // Find a guardian by ID
    public Guardian findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dtype = rs.getString("dtype");
                if ("guardian".equals(dtype)) {
                    // Return Guardian object (no specialization for Guardian)
                    return new Guardian(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email_id"),
                            rs.getInt("age")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return null; // Return null if no guardian found
    }

    // Delete guardian by ID
    public boolean deleteGuardian(Long id) {
        String sql = "DELETE FROM users WHERE id = ? AND dtype = 'guardian'";
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

    // Update guardian details
    public Guardian editGuardian(Long id, Guardian updatedGuardian) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email_id = ?, age = ?, dtype = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedGuardian.getFirstname());
            stmt.setString(2, updatedGuardian.getLastname());
            stmt.setString(3, updatedGuardian.getEmail());
            stmt.setInt(4, updatedGuardian.getAge());
            stmt.setString(5, "guardian");  // Specifying dtype as "guardian"
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedGuardian.setId(id);
                return updatedGuardian; // Return the updated guardian if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    // Get all guardians from the database
    public List<Guardian> findAll() {
        List<Guardian> guardians = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE dtype = 'guardian'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Guardian guardian = new Guardian(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getInt("age")
                );

                guardians.add(guardian);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return guardians;
    }
}
