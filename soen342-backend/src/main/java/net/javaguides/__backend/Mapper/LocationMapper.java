package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.entity.Location;

public class LocationMapper {

    public static LocationDto mapToLocationDto(Location location) {
        return new LocationDto(
                location.getId(),
                location.getName(),
                location.getCity()
        );
    }

    public static Location mapToLocation(LocationDto locationDto) {
        return new Location(
                locationDto.getId(),
                locationDto.getName(),
                locationDto.getCity(),
                null // No need to set schedule here, as it's already handled in entity relationships
        );
    }
}
