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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GuardianServiceImpl implements GuardianService {

    private final GuardianRepository guardianRepository;

    @Override
    public GuardianDto createGuardian(GuardianDto guardianDto) {
        Guardian guardian = GuardianMapper.mapToGuardian(guardianDto);
        Guardian savedGuardian = guardianRepository.save(guardian);
        return GuardianMapper.mapToGuardianDto(savedGuardian);
    }

    @Override
    public GuardianDto getGuardianById(Long guardianId) {
        Guardian guardian = guardianRepository.findById(guardianId);
        if (guardian == null) {
            throw new ResourceNotFoundException("Guardian with id " + guardianId + " does not exist");
        }
        return GuardianMapper.mapToGuardianDto(guardian);
    }

    @Override
    public void deleteGuardian(Long id) {
        Guardian guardian = guardianRepository.findById(id);
        if (guardian == null) {
            throw new ResourceNotFoundException("Guardian with id " + id + " does not exist");
        }

        boolean deleted = guardianRepository.deleteGuardian(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Guardian with id " + id + " could not be deleted");
        }
    }

    @Override
    public GuardianDto updateGuardian(Long id, GuardianDto updatedGuardianDto) {
        Guardian existingGuardian = guardianRepository.findById(id);
        if (existingGuardian == null) {
            throw new ResourceNotFoundException("Guardian with id " + id + " does not exist");
        }

        Guardian updatedGuardian = GuardianMapper.mapToGuardian(updatedGuardianDto);

        // Update guardian details
        existingGuardian.setFirstname(updatedGuardian.getFirstname());
        existingGuardian.setLastname(updatedGuardian.getLastname());
        existingGuardian.setEmail(updatedGuardian.getEmail());
        existingGuardian.setAge(updatedGuardian.getAge());

        Guardian savedGuardian = guardianRepository.editGuardian(id, existingGuardian);  // Assuming editGuardian saves and returns updated guardian
        return GuardianMapper.mapToGuardianDto(savedGuardian);
    }

    @Override
    public List<GuardianDto> getAllGuardians() {
        List<Guardian> guardians = guardianRepository.findAll();
        return guardians.stream()
                .map(GuardianMapper::mapToGuardianDto)
                .collect(Collectors.toList());
    }
}
