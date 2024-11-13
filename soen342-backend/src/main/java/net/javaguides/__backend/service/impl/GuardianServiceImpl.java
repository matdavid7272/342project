package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.GuardianDto;
import net.javaguides.__backend.entity.Guardian;
import net.javaguides.__backend.Mapper.GuardianMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.GuardianRepository;
import net.javaguides.__backend.service.GuardianService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper; // Inject GuardianMapper

    @Override
    public GuardianDto createGuardian(GuardianDto guardianDto) {
        // Check if a guardian with the same email already exists
        Optional<Guardian> existingGuardian = guardianRepository.findByEmail(guardianDto.getEmail());
        if (existingGuardian.isPresent()) {
            // If email exists, throw a custom exception
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert GuardianDto to Guardian entity using injected GuardianMapper
        Guardian guardian = guardianMapper.mapToGuardian(guardianDto);

        // Save the guardian entity
        Guardian savedGuardian = guardianRepository.save(guardian);

        // Convert saved guardian back to DTO
        return guardianMapper.mapToGuardianDto(savedGuardian);
    }

    @Override
    public GuardianDto getGuardianById(Long guardianId) {
        // Try to find the guardian by ID
        Optional<Guardian> guardianOptional = guardianRepository.findById(guardianId);
        if (!guardianOptional.isPresent()) {
            // Handle case where guardian is not found
            throw new ResourceNotFoundException("Guardian with id " + guardianId + " does not exist");
        }
        Guardian guardian = guardianOptional.get();
        return guardianMapper.mapToGuardianDto(guardian);
    }

    @Override
    public void deleteGuardian(Long id) {
        // Try to find the guardian by ID
        Optional<Guardian> guardianOptional = guardianRepository.findById(id);
        if (!guardianOptional.isPresent()) {
            // Handle case where guardian is not found
            throw new ResourceNotFoundException("Guardian with id " + id + " does not exist");
        }
        // Delete the found guardian
        guardianRepository.delete(guardianOptional.get());
    }

    @Override
    public GuardianDto updateGuardian(Long id, GuardianDto updatedGuardianDto) {
        // Try to find the guardian by ID
        Optional<Guardian> existingGuardianOptional = guardianRepository.findById(id);
        if (!existingGuardianOptional.isPresent()) {
            throw new ResourceNotFoundException("Guardian with id " + id + " does not exist");
        }

        // Check if another guardian with the same email exists (excluding the current
        // one)
        Optional<Guardian> guardianWithSameEmail = guardianRepository.findByEmail(updatedGuardianDto.getEmail());
        if (guardianWithSameEmail.isPresent() && !guardianWithSameEmail.get().getId().equals(id)) {
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert updated DTO to entity using injected GuardianMapper
        Guardian updatedGuardian = guardianMapper.mapToGuardian(updatedGuardianDto);
        updatedGuardian.setId(id);

        // Save the updated guardian
        Guardian savedGuardian = guardianRepository.save(updatedGuardian);
        return guardianMapper.mapToGuardianDto(savedGuardian);
    }

    @Override
    public List<GuardianDto> getAllGuardians() {
        // Get all guardians from the repository
        List<Guardian> guardians = guardianRepository.findAll();
        // Convert guardians to DTOs and return as a list
        return guardians.stream()
                .map(guardianMapper::mapToGuardianDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuardianDto getGuardianByEmail(String guardianEmail) {
        // Try to find the guardian by ID
        Optional<Guardian> guardianOptional = guardianRepository.findByEmail(guardianEmail);
        if (!guardianOptional.isPresent()) {
            // Handle case where guardian is not found
            System.out.println("Guardian with email " + guardianEmail + " does not exist.");
            return null;
        }
        Guardian guardian = guardianOptional.get();
        return guardianMapper.mapToGuardianDto(guardian);
    }
}
