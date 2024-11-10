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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Override
    public AdministratorDto createAdministrator(AdministratorDto administratorDto) {
        Administrator administrator = AdministratorMapper.mapToAdministrator(administratorDto);
        Administrator savedAdministrator = administratorRepository.save(administrator);
        return AdministratorMapper.mapToAdministratorDto(savedAdministrator);
    }

    @Override
    public AdministratorDto getAdministratorById(Long administratorId) {
        Administrator administrator = administratorRepository.findById(administratorId);
        if (administrator == null) {
            throw new ResourceNotFoundException("Administrator with id " + administratorId + " does not exist");
        }
        return AdministratorMapper.mapToAdministratorDto(administrator);
    }

    @Override
    public void deleteAdministrator(Long id) {
        Administrator administrator = administratorRepository.findById(id);
        if (administrator == null) {
            throw new ResourceNotFoundException("Administrator with id " + id + " does not exist");
        }

        boolean deleted = administratorRepository.deleteAdministrator(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Administrator with id " + id + " could not be deleted");
        }
    }

    @Override
    public AdministratorDto updateAdministrator(Long id, AdministratorDto updatedAdministratorDto) {
        Administrator existingAdministrator = administratorRepository.findById(id);
        if (existingAdministrator == null) {
            throw new ResourceNotFoundException("Administrator with id " + id + " does not exist");
        }

        Administrator updatedAdministrator = AdministratorMapper.mapToAdministrator(updatedAdministratorDto);

        // Update administrator details
        existingAdministrator.setFirstname(updatedAdministrator.getFirstname());
        existingAdministrator.setLastname(updatedAdministrator.getLastname());
        existingAdministrator.setEmail(updatedAdministrator.getEmail());
        existingAdministrator.setAge(updatedAdministrator.getAge());

        Administrator savedAdministrator = administratorRepository.editAdministrator(id, existingAdministrator);
        return AdministratorMapper.mapToAdministratorDto(savedAdministrator);
    }

    @Override
    public List<AdministratorDto> getAllAdministrators() {
        List<Administrator> administrators = administratorRepository.findAll();
        return administrators.stream()
                .map(AdministratorMapper::mapToAdministratorDto)
                .collect(Collectors.toList());
    }
}
