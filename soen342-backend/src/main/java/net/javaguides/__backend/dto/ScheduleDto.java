package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {

    private Long id;
    private List<Long> timeSlotIds;    // List of TimeSlot IDs
    private Long locationId;           // The ID of the associated Location
}
