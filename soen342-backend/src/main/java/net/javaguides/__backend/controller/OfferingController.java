package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.service.OfferingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/offerings")
public class OfferingController {

    private OfferingService offeringService;

    // Build add Offering REST API
    @PostMapping
    public ResponseEntity<OfferingDto> createOffering(@RequestBody OfferingDto offeringDto) {
        OfferingDto savedOffering = offeringService.createOffering(offeringDto);
        return new ResponseEntity<>(savedOffering, HttpStatus.CREATED);
    }

    // Build get Offering by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<OfferingDto> getOfferingById(@PathVariable("id") Long offeringId) {
        OfferingDto offeringDto = offeringService.getOfferingById(offeringId);
        return ResponseEntity.ok(offeringDto);
    }

    // Get all Offerings REST API
    @GetMapping
    public ResponseEntity<List<OfferingDto>> getAllOfferings() {
        List<OfferingDto> offerings = offeringService.getAllOfferings();
        return ResponseEntity.ok(offerings);
    }

    // Delete Offering by ID REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOffering(@PathVariable("id") Long offeringId) {
        offeringService.deleteOffering(offeringId);
        return ResponseEntity.noContent().build();
    }

    // Update Offering by ID REST API
    @PutMapping("{id}")
    public ResponseEntity<OfferingDto> updateOffering(@PathVariable("id") Long offeringId,
                                                      @RequestBody OfferingDto updatedOffering) {
        OfferingDto offeringDto = offeringService.updateOffering(offeringId, updatedOffering);
        return ResponseEntity.ok(offeringDto);
    }
}
