package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.LocationDto;

import java.util.List;

public interface LocationService {

    LocationDto createLocation(LocationDto locationDto);

    LocationDto getLocationById(Long locationId);

    void deleteLocation(Long id);

    LocationDto updateLocation(Long id, LocationDto locationDto);

    List<LocationDto> getAllLocations();
}
