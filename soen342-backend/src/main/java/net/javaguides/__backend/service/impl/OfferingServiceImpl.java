package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Offering;
import net.javaguides.__backend.Mapper.OfferingMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.OfferingRepository;
import net.javaguides.__backend.service.OfferingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;  // Inject OfferingMapper

    @Override
    public OfferingDto createOffering(OfferingDto offeringDto) {
        // Convert OfferingDto to Offering entity using injected OfferingMapper
        Offering offering = offeringMapper.mapToOffering(offeringDto);
        // Save the offering entity
        Offering savedOffering = offeringRepository.save(offering);
        // Convert saved offering back to DTO
        return offeringMapper.mapToOfferingDto(savedOffering);
    }

    @Override
    public OfferingDto getOfferingById(Long offeringId) {
        // Try to find the offering by ID
        Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);
        if (!offeringOptional.isPresent()) {
            // Handle case where offering is not found
            throw new ResourceNotFoundException("Offering with id " + offeringId + " does not exist");
        }
        Offering offering = offeringOptional.get();
        return offeringMapper.mapToOfferingDto(offering);
    }

    @Override
    public void deleteOffering(Long id) {
        // Try to find the offering by ID
        Optional<Offering> offeringOptional = offeringRepository.findById(id);
        if (!offeringOptional.isPresent()) {
            // Handle case where offering is not found
            throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
        }
        // Delete the found offering
        offeringRepository.delete(offeringOptional.get());
    }

    @Override
    public OfferingDto updateOffering(Long id, OfferingDto updatedOfferingDto) {
        // Try to find the offering by ID
        Optional<Offering> existingOfferingOptional = offeringRepository.findById(id);
        if (!existingOfferingOptional.isPresent()) {
            // Handle case where offering is not found
            throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
        }

        // Convert updated DTO to entity using injected OfferingMapper
        Offering updatedOffering = offeringMapper.mapToOffering(updatedOfferingDto);
        updatedOffering.setId(id);  // Ensure the updated offering has the correct ID

        // Save the updated offering
        Offering savedOffering = offeringRepository.save(updatedOffering);
        return offeringMapper.mapToOfferingDto(savedOffering);
    }

    @Override
    public List<OfferingDto> getAllOfferings() {
        // Get all offerings from the repository
        List<Offering> offerings = offeringRepository.findAll();
        // Convert offerings to DTOs and return as a list
        return offerings.stream()
                .map(offeringMapper::mapToOfferingDto)
                .collect(Collectors.toList());
    }
}
