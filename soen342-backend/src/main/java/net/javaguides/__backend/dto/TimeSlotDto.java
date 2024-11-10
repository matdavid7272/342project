package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDto {

    private Long id;
    private String Day;
    private String startTime;
    private String endTime;
    private Long scheduleId;  // The ID of the associated Schedule
}
