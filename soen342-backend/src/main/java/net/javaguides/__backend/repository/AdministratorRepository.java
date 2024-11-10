package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Administrator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AdministratorRepository {

    private final DataSource dataSource;

    public AdministratorRepository(DataSource dataSource) {
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

    // Save an administrator to the database
    public Administrator save(Administrator administrator) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, age, dtype) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, administrator.getFirstname());
            stmt.setString(2, administrator.getLastname());
            stmt.setString(3, administrator.getEmail());
            stmt.setInt(4, administrator.getAge());
            stmt.setString(5, "administrator");  // Specifying dtype as "administrator"

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    administrator.setId(generatedKeys.getLong(1));  // Get the generated ID and set it
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return administrator;
    }

    // Find an administrator by ID
    public Administrator findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dtype = rs.getString("dtype");
                if ("administrator".equals(dtype)) {
                    // Return Administrator object (no specialization for Administrator)
                    return new Administrator(
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
        return null; // Return null if no administrator found
    }

    // Delete administrator by ID
    public boolean deleteAdministrator(Long id) {
        String sql = "DELETE FROM users WHERE id = ? AND dtype = 'administrator'";
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

    // Update administrator details
    public Administrator editAdministrator(Long id, Administrator updatedAdministrator) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email_id = ?, age = ?, dtype = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedAdministrator.getFirstname());
            stmt.setString(2, updatedAdministrator.getLastname());
            stmt.setString(3, updatedAdministrator.getEmail());
            stmt.setInt(4, updatedAdministrator.getAge());
            stmt.setString(5, "administrator");  // Specifying dtype as "administrator"
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedAdministrator.setId(id);
                return updatedAdministrator; // Return the updated administrator if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    // Get all administrators from the database
    public List<Administrator> findAll() {
        List<Administrator> administrators = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE dtype = 'administrator'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Administrator administrator = new Administrator(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getInt("age")
                );

                administrators.add(administrator);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return administrators;
    }
}
