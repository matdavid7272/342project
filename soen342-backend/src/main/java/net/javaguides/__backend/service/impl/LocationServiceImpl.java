package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.Mapper.LocationMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.LocationRepository;
import net.javaguides.__backend.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public LocationDto createLocation(LocationDto locationDto) {
        Location location = LocationMapper.mapToLocation(locationDto);
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.mapToLocationDto(savedLocation);
    }

    @Override
    public LocationDto getLocationById(Long locationId) {
        Location location = locationRepository.findById(locationId);
        if (location == null) {
            throw new ResourceNotFoundException("Location with id " + locationId + " does not exist");
        }
        return LocationMapper.mapToLocationDto(location);
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id);
        if (location == null) {
            throw new ResourceNotFoundException("Location with id " + id + " does not exist");
        }

        boolean deleted = locationRepository.deleteLocation(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Location with id " + id + " could not be deleted");
        }
    }

    @Override
    public LocationDto updateLocation(Long id, LocationDto updatedLocationDto) {
        Location existingLocation = locationRepository.findById(id);
        if (existingLocation == null) {
            throw new ResourceNotFoundException("Location with id " + id + " does not exist");
        }

        Location updatedLocation = LocationMapper.mapToLocation(updatedLocationDto);

        // Update location details
        existingLocation.setAddress(updatedLocation.getAddress());
        existingLocation.setLocationType(updatedLocation.getLocationType());
        existingLocation.setSchedule(updatedLocation.getSchedule());

        Location savedLocation = locationRepository.editLocation(id, existingLocation);  // Assuming editLocation saves and returns updated location
        return LocationMapper.mapToLocationDto(savedLocation);
    }

    @Override
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(LocationMapper::mapToLocationDto)
                .collect(Collectors.toList());
    }
}
