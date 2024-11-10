package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.Mapper.LocationMapper; // Assuming you have a LocationMapper for conversion
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.LocationRepository;
import net.javaguides.__backend.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = LocationMapper.mapToLocation(locationDto); // Assuming you have a mapToLocation method
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.mapToLocationDto(savedLocation); // Assuming you have a mapToLocationDto method
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        Optional<Location> locationOptional = locationRepository.findById(locationId);
        if (!locationOptional.isPresent()) {
            // Handle the case where location is not found
            throw new ResourceNotFoundException("Location with id " + locationId + " does not exist");
        }
        Location location = locationOptional.get();
        return LocationMapper.mapToLocationDto(location);
    }

    @Override
    public void deleteLocation(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if (!locationOptional.isPresent()) {
            // Handle the case where location is not found
            throw new ResourceNotFoundException("Location with id " + id + " does not exist");
        }
        locationRepository.delete(locationOptional.get()); // Delete the found location
    }

    @Override
    public LocationDto updateLocation(Long id, LocationDto updatedLocationDto) {
        Optional<Location> existingLocationOptional = locationRepository.findById(id);
        if (!existingLocationOptional.isPresent()) {
            // Handle the case where location is not found
            throw new ResourceNotFoundException("Location with id " + id + " does not exist");
        }

        // Map updatedLocationDto to Location object
        Location updatedLocation = LocationMapper.mapToLocation(updatedLocationDto);
        updatedLocation.setId(id);  // Ensure the updated location has the correct ID

        // Save updated location
        Location savedLocation = locationRepository.save(updatedLocation);
        return LocationMapper.mapToLocationDto(savedLocation);
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll(); // Assuming findAll() fetches all Location records
        return locations.stream()
                .map(LocationMapper::mapToLocationDto) // Convert each Location to LocationDto
                .collect(Collectors.toList());
    }
}
