package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Client;
import net.javaguides.__backend.entity.User;
import org.springframework.dao.DuplicateKeyException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository {

    private final DataSource dataSource;

    public ClientRepository(DataSource dataSource) {
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

    // Save a client to the database
    public Client save(Client client) {
        String sql = "INSERT INTO users (first_name, last_name, email_id, age, dtype) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, client.getFirstname());
            stmt.setString(2, client.getLastname());
            stmt.setString(3, client.getEmail());
            stmt.setInt(4, client.getAge());
            stmt.setString(5, "client");  // Specifying dtype as "client"

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));  // Get the generated ID and set it
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return client;
    }

    // Find a client by ID
    public Client findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String dtype = rs.getString("dtype");
                if ("client".equals(dtype)) {
                    // Return Client object (no specialization for Client)
                    return new Client(
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
        return null; // Return null if no client found
    }

    // Delete client by ID
    public boolean deleteClient(Long id) {
        String sql = "DELETE FROM users WHERE id = ? AND dtype = 'client'";
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

    // Update client details
    public Client editClient(Long id, Client updatedClient) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email_id = ?, age = ?, dtype = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedClient.getFirstname());
            stmt.setString(2, updatedClient.getLastname());
            stmt.setString(3, updatedClient.getEmail());
            stmt.setInt(4, updatedClient.getAge());
            stmt.setString(5, "client");  // Specifying dtype as "client"
            stmt.setLong(6, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedClient.setId(id);
                return updatedClient; // Return the updated client if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Email already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    // Get all clients from the database
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE dtype = 'client'";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_id"),
                        rs.getInt("age")
                );

                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return clients;
    }
}
