package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.GuardianDto;
import net.javaguides.__backend.entity.Guardian;
import org.springframework.stereotype.Component;

@Component
public class GuardianMapper {

    // Map Guardian entity to GuardianDto
    public GuardianDto mapToGuardianDto(Guardian guardian) {
        return new GuardianDto(
                guardian.getId(),
                guardian.getLastname(),
                guardian.getFirstname(),
                guardian.getEmail(),
                guardian.getAge()
        );
    }

    // Map GuardianDto to Guardian entity
    public Guardian mapToGuardian(GuardianDto guardianDto) {
        return new Guardian(
                guardianDto.getId(),
                guardianDto.getLastname(),
                guardianDto.getFirstname(),
                guardianDto.getEmail(),
                guardianDto.getAge()
        );
    }
}
