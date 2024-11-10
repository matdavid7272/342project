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

    // Create location
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto){
        LocationDto saveLocation = locationService.createLocation(locationDto);
        return new ResponseEntity<>(saveLocation, HttpStatus.CREATED);
    }

    // Get location by ID
    @GetMapping("{id}")
    public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") Long locationId){
        LocationDto locationDto = locationService.getLocationById(locationId);
        return ResponseEntity.ok(locationDto);
    }

    // Get all locations
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        List<LocationDto> locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    // Delete location by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }

    // Update location by ID
    @PutMapping("{id}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") Long locationId,
                                                      @RequestBody LocationDto updatedLocation){
        LocationDto locationDto = locationService.updateLocation(locationId, updatedLocation);
        return ResponseEntity.ok(locationDto);
    }
}
