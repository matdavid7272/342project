package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.javaguides.__backend.entity.LocationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {

    private Long id;
    private String address;
    private LocationType locationType;  // Use LocationType directly as it is an enum
    private Long scheduleId;  // Store schedule as a reference (ID)
}
