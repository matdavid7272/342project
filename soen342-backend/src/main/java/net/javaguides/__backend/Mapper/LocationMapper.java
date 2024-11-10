package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.entity.LocationType;  // Import LocationType enum

public class LocationMapper {

    // Map Location entity to LocationDto
    public static LocationDto mapToLocationDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getAddress(),
                location.getLocationType(),  // Directly map the LocationType enum
                location.getSchedule() != null ? location.getSchedule().getId() : null  // Optional schedule ID
        );
    }

    // Map LocationDto to Location entity
    public static Location mapToLocation(LocationDto locationDto) {
        Location location = new Location();
        location.setId(locationDto.getId());
        location.setAddress(locationDto.getAddress());
        location.setLocationType(locationDto.getLocationType());  // Directly map the LocationType enum
        // Set the Schedule entity if needed, or leave it null if not provided
        return location;
    }
}
