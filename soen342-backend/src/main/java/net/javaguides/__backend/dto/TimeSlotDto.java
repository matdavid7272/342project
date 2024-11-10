package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {

    private Long id;              // ID of the TimeSlot
    private LocalTime startTime;  // Start time of the time slot
    private LocalTime endTime;    // End time of the time slot
    private Long scheduleId;      // ID of the associated schedule (Foreign key reference)

}
