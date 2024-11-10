package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.LocationDto;
import net.javaguides.__backend.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private LocationService locationService;

    // Build add Location REST API
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        LocationDto savedLocation = locationService.createLocation(locationDto);
        return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
    }

    // Build get Location by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") Long locationId) {
        LocationDto locationDto = locationService.getLocationById(locationId);
        return ResponseEntity.ok(locationDto);
    }

    // Get all Locations REST API
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    // Delete Location by ID REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }

    // Update Location by ID REST API
    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long locationId,
                                                      @RequestBody LocationDto updatedLocation) {
        LocationDto locationDto = locationService.updateLocation(locationId, updatedLocation);
        return ResponseEntity.ok(locationDto);
    }
}
