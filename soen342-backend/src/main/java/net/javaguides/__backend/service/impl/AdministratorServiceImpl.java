package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.AdministratorDto;
import net.javaguides.__backend.entity.Administrator;
import net.javaguides.__backend.Mapper.AdministratorMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.AdministratorRepository;
import net.javaguides.__backend.service.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;
    private final AdministratorMapper administratorMapper;  // Inject AdministratorMapper

    @Override
    public AdministratorDto createAdministrator(AdministratorDto administratorDto) {
        // Convert AdministratorDto to Administrator entity using injected AdministratorMapper
        Administrator administrator = administratorMapper.mapToAdministrator(administratorDto);
        // Save the administrator entity
        Administrator savedAdministrator = administratorRepository.save(administrator);
        // Convert saved administrator back to DTO
        return administratorMapper.mapToAdministratorDto(savedAdministrator);
    }

    @Override
    public AdministratorDto getAdministratorById(Long administratorId) {
        // Try to find the administrator by ID
        Optional<Administrator> administratorOptional = administratorRepository.findById(administratorId);
        if (!administratorOptional.isPresent()) {
            // Handle case where administrator is not found
            throw new ResourceNotFoundException("Administrator with id " + administratorId + " does not exist");
        }
        Administrator administrator = administratorOptional.get();
        return administratorMapper.mapToAdministratorDto(administrator);
    }

    @Override
    public void deleteAdministrator(Long id) {
        // Try to find the administrator by ID
        Optional<Administrator> administratorOptional = administratorRepository.findById(id);
        if (!administratorOptional.isPresent()) {
            // Handle case where administrator is not found
            throw new ResourceNotFoundException("Administrator with id " + id + " does not exist");
        }
        // Delete the found administrator
        administratorRepository.delete(administratorOptional.get());
    }

    @Override
    public AdministratorDto updateAdministrator(Long id, AdministratorDto updatedAdministratorDto) {
        // Try to find the administrator by ID
        Optional<Administrator> existingAdministratorOptional = administratorRepository.findById(id);
        if (!existingAdministratorOptional.isPresent()) {
            // Handle case where administrator is not found
            throw new ResourceNotFoundException("Administrator with id " + id + " does not exist");
        }

        // Convert updated DTO to entity using injected AdministratorMapper
        Administrator updatedAdministrator = administratorMapper.mapToAdministrator(updatedAdministratorDto);
        updatedAdministrator.setId(id);  // Ensure the updated administrator has the correct ID

        // Save the updated administrator
        Administrator savedAdministrator = administratorRepository.save(updatedAdministrator);
        return administratorMapper.mapToAdministratorDto(savedAdministrator);
    }

    @Override
    public List<AdministratorDto> getAllAdministrators() {
        // Get all administrators from the repository
        List<Administrator> administrators = administratorRepository.findAll();
        // Convert administrators to DTOs and return as a list
        return administrators.stream()
                .map(administratorMapper::mapToAdministratorDto)
                .collect(Collectors.toList());
    }
}
