package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Schedule;
import net.javaguides.__backend.entity.TimeSlot;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepository {

    private final DataSource dataSource;

    public ScheduleRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Schedule save(Schedule schedule) {
        String scheduleSql = "INSERT INTO schedule (dtype) VALUES (?)";
        String dtype = "schedule"; // Default dtype value for schedules

        try (Connection connection = dataSource.getConnection();
             PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql, Statement.RETURN_GENERATED_KEYS)) {

            // Insert into schedules table
            scheduleStmt.setString(1, dtype);
            int affectedRows = scheduleStmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = scheduleStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    schedule.setId(generatedKeys.getLong(1));
                }

                // Insert associated time slots
                for (TimeSlot timeSlot : schedule.getTimeSlots()) {
                    saveTimeSlot(timeSlot, schedule.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    private void saveTimeSlot(TimeSlot timeSlot, Long scheduleId) throws SQLException {
        String timeSlotSql = "INSERT INTO time_slot (start_time, end_time, schedule_id) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement timeSlotStmt = connection.prepareStatement(timeSlotSql, Statement.RETURN_GENERATED_KEYS)) {
            timeSlotStmt.setTime(1, Time.valueOf(timeSlot.getStartTime()));
            timeSlotStmt.setTime(2, Time.valueOf(timeSlot.getEndTime()));
            timeSlotStmt.setLong(3, scheduleId);

            timeSlotStmt.executeUpdate();
        }
    }

    public Schedule findById(Long id) {
        String scheduleSql = "SELECT * FROM schedule WHERE id = ?";
        String timeSlotSql = "SELECT * FROM time_slot WHERE schedule_id = ?";
        Schedule schedule = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {
            scheduleStmt.setLong(1, id);
            ResultSet rs = scheduleStmt.executeQuery();

            if (rs.next()) {
                schedule = new Schedule();
                schedule.setId(rs.getLong("id"));

                // Retrieve associated time slots
                List<TimeSlot> timeSlots = new ArrayList<>();
                try (PreparedStatement timeSlotStmt = connection.prepareStatement(timeSlotSql)) {
                    timeSlotStmt.setLong(1, id);
                    ResultSet tsRs = timeSlotStmt.executeQuery();

                    while (tsRs.next()) {
                        TimeSlot timeSlot = new TimeSlot(
                                tsRs.getLong("id"),
                                tsRs.getTime("start_time").toLocalTime(),
                                tsRs.getTime("end_time").toLocalTime()
                        );
                        timeSlots.add(timeSlot);
                    }
                }
                schedule.setTimeSlots(timeSlots);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public boolean deleteSchedule(Long id) {
        String timeSlotSql = "DELETE FROM time_slot WHERE schedule_id = ?";
        String scheduleSql = "DELETE FROM schedule WHERE id = ?";

        try (Connection connection = dataSource.getConnection()) {
            // Delete associated time slots
            try (PreparedStatement timeSlotStmt = connection.prepareStatement(timeSlotSql)) {
                timeSlotStmt.setLong(1, id);
                timeSlotStmt.executeUpdate();
            }

            // Delete the schedule
            try (PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {
                scheduleStmt.setLong(1, id);
                int affectedRows = scheduleStmt.executeUpdate();
                return affectedRows > 0; // Return true if a row was deleted
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Schedule editSchedule(Long id, Schedule updatedSchedule) {
        String scheduleSql = "UPDATE schedule SET dtype = ? WHERE id = ?";
        String dtype = "schedule"; // Default dtype

        try (Connection connection = dataSource.getConnection();
             PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql)) {

            // Update the schedule
            scheduleStmt.setString(1, dtype);
            scheduleStmt.setLong(2, id);
            int affectedRows = scheduleStmt.executeUpdate();

            if (affectedRows > 0) {
                // Update associated time slots
                deleteTimeSlotsByScheduleId(id);
                for (TimeSlot timeSlot : updatedSchedule.getTimeSlots()) {
                    saveTimeSlot(timeSlot, id);
                }
                updatedSchedule.setId(id);
                return updatedSchedule;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteTimeSlotsByScheduleId(Long scheduleId) throws SQLException {
        String sql = "DELETE FROM time_slot WHERE schedule_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, scheduleId);
            stmt.executeUpdate();
        }
    }

    public List<Schedule> findAll() {
        List<Schedule> schedules = new ArrayList<>();
        String scheduleSql = "SELECT * FROM schedule";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement scheduleStmt = connection.prepareStatement(scheduleSql);
             ResultSet rs = scheduleStmt.executeQuery()) {

            while (rs.next()) {
                Long scheduleId = rs.getLong("id");
                Schedule schedule = new Schedule();
                schedule.setId(scheduleId);

                // Retrieve associated time slots
                List<TimeSlot> timeSlots = findTimeSlotsByScheduleId(scheduleId);
                schedule.setTimeSlots(timeSlots);

                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    private List<TimeSlot> findTimeSlotsByScheduleId(Long scheduleId) throws SQLException {
        List<TimeSlot> timeSlots = new ArrayList<>();
        String timeSlotSql = "SELECT * FROM time_slot WHERE schedule_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement timeSlotStmt = connection.prepareStatement(timeSlotSql)) {
            timeSlotStmt.setLong(1, scheduleId);
            ResultSet tsRs = timeSlotStmt.executeQuery();

            while (tsRs.next()) {
                TimeSlot timeSlot = new TimeSlot(
                        tsRs.getLong("id"),
                        tsRs.getTime("start_time").toLocalTime(),
                        tsRs.getTime("end_time").toLocalTime()
                );
                timeSlots.add(timeSlot);
            }
        }
        return timeSlots;
    }
}
