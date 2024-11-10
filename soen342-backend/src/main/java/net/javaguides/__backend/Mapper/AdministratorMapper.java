package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.AdministratorDto;
import net.javaguides.__backend.entity.Administrator;

public class AdministratorMapper {

    // Map Administrator entity to AdministratorDto
    public static AdministratorDto mapToAdministratorDto(Administrator administrator) {
        return new AdministratorDto(
                administrator.getId(),
                administrator.getLastname(),
                administrator.getFirstname(),
                administrator.getEmail(),
                administrator.getAge()
        );
    }

    // Map AdministratorDto to Administrator entity
    public static Administrator mapToAdministrator(AdministratorDto administratorDto) {
        return new Administrator(
                administratorDto.getId(),
                administratorDto.getLastname(),
                administratorDto.getFirstname(),
                administratorDto.getEmail(),
                administratorDto.getAge()
        );
    }
}
