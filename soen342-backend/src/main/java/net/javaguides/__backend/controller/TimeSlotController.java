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
@RequestMapping("/api/time_slots")
public class TimeSlotController {

    private TimeSlotService timeSlotService;

    // Build add TimeSlot REST API
    @PostMapping
    public ResponseEntity<TimeSlotDto> createTimeSlot(@RequestBody TimeSlotDto timeSlotDto) {
        TimeSlotDto savedTimeSlot = timeSlotService.createTimeSlot(timeSlotDto);
        return new ResponseEntity<>(savedTimeSlot, HttpStatus.CREATED);
    }

    // Build get TimeSlot by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<TimeSlotDto> getTimeSlotById(@PathVariable("id") Long timeSlotId) {
        TimeSlotDto timeSlotDto = timeSlotService.getTimeSlotById(timeSlotId);
        return ResponseEntity.ok(timeSlotDto);
    }

    // Get all TimeSlots REST API
    @GetMapping
    public ResponseEntity<List<TimeSlotDto>> getAllTimeSlots() {
        List<TimeSlotDto> timeSlots = timeSlotService.getAllTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    // Delete TimeSlot by ID REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable("id") Long timeSlotId) {
        timeSlotService.deleteTimeSlot(timeSlotId);
        return ResponseEntity.noContent().build();
    }

    // Update TimeSlot by ID REST API
    @PutMapping("{id}")
    public ResponseEntity<TimeSlotDto> updateTimeSlot(@PathVariable("id") Long timeSlotId,
                                                      @RequestBody TimeSlotDto updatedTimeSlot) {
        TimeSlotDto timeSlotDto = timeSlotService.updateTimeSlot(timeSlotId, updatedTimeSlot);
        return ResponseEntity.ok(timeSlotDto);
    }
}
