package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.AdministratorDto;
import net.javaguides.__backend.service.AdministratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/administrators")
public class AdministratorController {

    private AdministratorService administratorService;

    // Build add rest api for Administrator
    @PostMapping
    public ResponseEntity<AdministratorDto> createAdministrator(@RequestBody AdministratorDto administratorDto){
        AdministratorDto savedAdministrator = administratorService.createAdministrator(administratorDto);
        return new ResponseEntity<>(savedAdministrator, HttpStatus.CREATED);
    }

    // Build get Administrator rest api by ID
    @GetMapping("{id}")
    public ResponseEntity<AdministratorDto> getAdministratorById(@PathVariable("id") Long administratorId){
        AdministratorDto administratorDto = administratorService.getAdministratorById(administratorId);
        return ResponseEntity.ok(administratorDto);
    }

    // Get all Administrators
    @GetMapping
    public ResponseEntity<List<AdministratorDto>> getAllAdministrators() {
        List<AdministratorDto> administrators = administratorService.getAllAdministrators();
        return ResponseEntity.ok(administrators);
    }

    // Delete Administrator by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAdministrator(@PathVariable("id") Long administratorId) {
        administratorService.deleteAdministrator(administratorId);
        return ResponseEntity.noContent().build();
    }

    // Update Administrator details
    @PutMapping("{id}")
    public ResponseEntity<AdministratorDto> updateAdministrator(@PathVariable("id") Long administratorId,
                                                                @RequestBody AdministratorDto updatedAdministrator){
        AdministratorDto administratorDto = administratorService.updateAdministrator(administratorId, updatedAdministrator);
        return ResponseEntity.ok(administratorDto);
    }

}
