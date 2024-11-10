package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.GuardianDto;
import net.javaguides.__backend.service.GuardianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/guardians") // Changed from "/api/clients" to "/api/guardians"
public class GuardianController {

    private GuardianService guardianService;

    // Build add rest api
    @PostMapping
    public ResponseEntity<GuardianDto> createGuardian(@RequestBody GuardianDto guardianDto){
        GuardianDto savedGuardian = guardianService.createGuardian(guardianDto);
        return new ResponseEntity<>(savedGuardian, HttpStatus.CREATED);
    }

    // Build get Guardian rest api by ID
    @GetMapping("{id}")
    public ResponseEntity<GuardianDto> getGuardianById(@PathVariable("id") Long guardianId){
        GuardianDto guardianDto = guardianService.getGuardianById(guardianId);
        return ResponseEntity.ok(guardianDto);
    }

    // Get all guardians
    @GetMapping
    public ResponseEntity<List<GuardianDto>> getAllGuardians() {
        List<GuardianDto> guardians = guardianService.getAllGuardians();
        return ResponseEntity.ok(guardians);
    }

    // Delete Guardian by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteGuardian(@PathVariable("id") Long guardianId) {
        guardianService.deleteGuardian(guardianId);
        return ResponseEntity.noContent().build();
    }

    // Update Guardian
    @PutMapping("{id}")
    public ResponseEntity<GuardianDto> updateGuardian(@PathVariable("id") Long guardianId,
                                                      @RequestBody GuardianDto updatedGuardian){
        GuardianDto guardianDto = guardianService.updateGuardian(guardianId, updatedGuardian);
        return ResponseEntity.ok(guardianDto);
    }
}
