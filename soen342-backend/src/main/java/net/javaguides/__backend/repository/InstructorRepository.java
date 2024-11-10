package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.entity.User;
import org.springframework.dao.DuplicateKeyException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InstructorRepository {

    private final DataSource dataSource;

    public InstructorRepository(DataSource dataSource) {
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

    // Save an instructor to the database
    public Instructor save(Instructor instructor) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, age, dtype, specialization) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, instructor.getFirstname());
            stmt.setString(2, instructor.getLastname());
            stmt.setString(3, instructor.getEmail());
            stmt.setInt(4, instructor.getAge());
            stmt.setString(5, "instructor");  // Specifying dtype as "instructor"
            stmt.setString(6, instructor.getSpecialization());  // Set specialization for Instructor

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    instructor.setId(generatedKeys.getLong(1));  // Get the generated ID and set it
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return instructor;
    }

    // Find an instructor by ID
    public Instructor findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dtype = rs.getString("dtype");
                if ("instructor".equals(dtype)) {
                    String specialization = rs.getString("specialization");
                    // Return Instructor object with the specialization
                    return new Instructor(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email_id"),
                            rs.getInt("age"),
                            rs.getString("specialization")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return null; // Return null if no instructor found
    }

    // Delete instructor by ID
    public boolean deleteInstructor(Long id) {
        String sql = "DELETE FROM users WHERE id = ? AND dtype = 'instructor'";
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

    // Update instructor details
    public Instructor editInstructor(Long id, Instructor updatedInstructor) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email_id = ?, age = ?, dtype = ?, specialization = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedInstructor.getFirstname());
            stmt.setString(2, updatedInstructor.getLastname());
            stmt.setString(3, updatedInstructor.getEmail());
            stmt.setInt(4, updatedInstructor.getAge());
            stmt.setString(5, "instructor");
            stmt.setString(6, updatedInstructor.getSpecialization()); // Update specialization
            stmt.setLong(7, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedInstructor.setId(id);
                return updatedInstructor; // Return the updated instructor if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    // Get all instructors from the database
    public List<Instructor> findAll() {
        List<Instructor> instructors = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE dtype = 'instructor'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String specialization = rs.getString("specialization");
                Instructor instructor = new Instructor(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getInt("age"),
                        specialization
                );

                instructors.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return instructors;
    }

}
