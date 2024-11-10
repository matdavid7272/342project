package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.TimeSlotDto;
import net.javaguides.__backend.service.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/time_slot") // Changed to "/api/timeSlots" for TimeSlot resources
public class TimeSlotController {

    private TimeSlotService timeSlotService;

    // Build add REST API for TimeSlot
    @PostMapping
    public ResponseEntity<TimeSlotDto> createTimeSlot(@RequestBody TimeSlotDto timeSlotDto){
        TimeSlotDto savedTimeSlot = timeSlotService.createTimeSlot(timeSlotDto);
        return new ResponseEntity<>(savedTimeSlot, HttpStatus.CREATED);
    }

    // Build get TimeSlot by ID
    @GetMapping("{id}")
    public ResponseEntity<TimeSlotDto> getTimeSlotById(@PathVariable("id") Long timeSlotId){
        TimeSlotDto timeSlotDto = timeSlotService.getTimeSlotById(timeSlotId);
        return ResponseEntity.ok(timeSlotDto);
    }

    // Get all TimeSlots
    @GetMapping
    public ResponseEntity<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlotDto> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    // Delete TimeSlot by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable("id") Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return ResponseEntity.noContent().build();
    }

    // Update TimeSlot
    @PutMapping("{id}")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable("id") Long timeSlotId,
                                                      @RequestBody TimeSlotDto updatedTimeSlot){
        TimeSlotDto timeSlotDto = timeSlotService.updateTimeSlot(timeSlotId, updatedTimeSlot);
        return ResponseEntity.ok(timeSlotDto);
    }
}
