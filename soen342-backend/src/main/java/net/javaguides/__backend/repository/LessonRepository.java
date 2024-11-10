package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Lesson;
import org.springframework.dao.DuplicateKeyException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

@Repository
public class LessonRepository {

    private final DataSource dataSource;

    public LessonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Lesson save(Lesson lesson) {
        String sql = "INSERT INTO lessons (name, duration, is_group) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, lesson.getName());
            stmt.setInt(2, lesson.getDuration());
            stmt.setBoolean(3, lesson.isGroup());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    lesson.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateKeyException("Error: Lesson already exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lesson;
    }

    public Lesson findById(Long id) {
        String sql = "SELECT * FROM lessons WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Lesson(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getBoolean("is_group")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteLesson(Long id) {
        String sql = "DELETE FROM lessons WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Lesson editLesson(Long id, Lesson updatedLesson) {
        String sql = "UPDATE lessons SET name = ?, duration = ?, is_group = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, updatedLesson.getName());
            stmt.setInt(2, updatedLesson.getDuration());
            stmt.setBoolean(3, updatedLesson.isGroup());
            stmt.setLong(4, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                updatedLesson.setId(id);
                return updatedLesson;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Lesson> findAll() {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lessons.add(new Lesson(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("duration"),
                        rs.getBoolean("is_group")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }
}
