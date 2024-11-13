package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.entity.Offering;
import net.javaguides.__backend.Mapper.OfferingMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.InstructorRepository;
import net.javaguides.__backend.repository.OfferingRepository;
import net.javaguides.__backend.service.OfferingService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;
    private final InstructorRepository instructorRepository;

    @Override
    @Transactional
    public boolean registerInstructorForOffering(Long instructorId, Long offeringId) {
        // Fetch the Instructor
        Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
        if (!instructorOptional.isPresent()) {
            throw new RuntimeException("Instructor not found.");
        }
        Instructor instructor = instructorOptional.get();

        // Fetch the Offering
        Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);
        if (!offeringOptional.isPresent()) {
            throw new RuntimeException("Offering not found.");
        }
        Offering offering = offeringOptional.get();

        // Check if the offering is available
        if (!offering.isAvailable()) {
            throw new RuntimeException("Offering is not available.");
        }

        // Check if the offering already has an instructor (if you want to enforce only
        // one instructor per offering)
        if (offering.getInstructor() != null) {
            return false; // Offering already has an instructor
        }

        // Assign the instructor to the offering
        offering.setInstructor(instructor);

        // Save the updated Offering entity
        offeringRepository.save(offering);

        return true;
    }

    @Override
    public OfferingDto createOffering(OfferingDto offeringDto) {
        Offering offering = offeringMapper.mapToOffering(offeringDto);
        Offering savedOffering = offeringRepository.save(offering);
        return offeringMapper.mapToOfferingDto(savedOffering);
    }

    @Override
    public OfferingDto getOfferingById(Long offeringId) {
        Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);
        if (!offeringOptional.isPresent()) {
            throw new ResourceNotFoundException("Offering with id " + offeringId + " does not exist");
        }
        Offering offering = offeringOptional.get();
        return offeringMapper.mapToOfferingDto(offering);
    }

    @Override
    public void deleteOffering(Long id) {
        Optional<Offering> offeringOptional = offeringRepository.findById(id);
        if (!offeringOptional.isPresent()) {
            throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
        }
        offeringRepository.delete(offeringOptional.get());
    }

    @Override
    public OfferingDto updateOffering(Long id, OfferingDto updatedOfferingDto) {
        Optional<Offering> existingOfferingOptional = offeringRepository.findById(id);
        if (!existingOfferingOptional.isPresent()) {
            throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
        }

        Offering updatedOffering = offeringMapper.mapToOffering(updatedOfferingDto);
        updatedOffering.setId(id);

        Offering savedOffering = offeringRepository.save(updatedOffering);
        return offeringMapper.mapToOfferingDto(savedOffering);
    }

    @Override
    public List<OfferingDto> getAllOfferings() {
        List<Offering> offerings = offeringRepository.findAll();
        return offerings.stream()
                .map(offeringMapper::mapToOfferingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferingDto> getOfferingsByInstructorId(Long instructorId) {
        List<Offering> offerings = offeringRepository.findByInstructorId(instructorId);
        return offerings.stream()
                .map(offeringMapper::mapToOfferingDto)
                .collect(Collectors.toList());
    }

}
