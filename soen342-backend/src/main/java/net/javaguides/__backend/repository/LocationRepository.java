package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.entity.LocationType;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepository {

    private final DataSource dataSource;

    public LocationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Save a location to the database
    public Location save(Location location) {
        String sql = "INSERT INTO locations (address, location_type, schedule_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, location.getAddress());
            stmt.setString(2, location.getLocationType().name());  // Assuming LocationType is an enum
            stmt.setLong(3, location.getSchedule() != null ? location.getSchedule().getId() : null);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    location.setId(generatedKeys.getLong(1));  // Get the generated ID and set it
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Location with the same address or schedule already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return location;
    }

    // Find a location by ID
    public Location findById(Long id) {
        String sql = "SELECT * FROM locations WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Location(
                        rs.getLong("id"),
                        rs.getString("address"),
                        LocationType.valueOf(rs.getString("location_type")), // Assuming LocationType is an enum
                        null // Assuming a schedule ID is returned but will handle separately in the service
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return null; // Return null if no location found
    }

    // Delete location by ID
    public boolean deleteLocation(Long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
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

    // Update location details
    public Location editLocation(Long id, Location updatedLocation) {
        String sql = "UPDATE locations SET address = ?, location_type = ?, schedule_id = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedLocation.getAddress());
            stmt.setString(2, updatedLocation.getLocationType().name()); // Assuming LocationType is an enum
            stmt.setLong(3, updatedLocation.getSchedule() != null ? updatedLocation.getSchedule().getId() : null);
            stmt.setLong(4, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedLocation.setId(id);
                return updatedLocation; // Return the updated location if successful
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Location with the same address or schedule already exists");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle other exceptions
        }
        return null; // Return null if the update was unsuccessful
    }

    // Get all locations from the database
    public List<Location> findAll() {
        List<Location> locations = new ArrayList<>();
        String sql = "SELECT * FROM locations";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Location location = new Location(
                        rs.getLong("id"),
                        rs.getString("address"),
                        LocationType.valueOf(rs.getString("location_type")), // Assuming LocationType is an enum
                        null // Assuming schedule handling will be done elsewhere
                );

                locations.add(location);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        }
        return locations;
    }
}
