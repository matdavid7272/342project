package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.AdministratorDto;
import net.javaguides.__backend.entity.Administrator;

import java.util.List;

public interface AdministratorService {

    AdministratorDto createAdministrator(AdministratorDto administratorDto);

    AdministratorDto getAdministratorById(Long administratorId);

    void deleteAdministrator(Long id);

    AdministratorDto updateAdministrator(Long id, AdministratorDto administratorDto);

    List<AdministratorDto> getAllAdministrators();
}
