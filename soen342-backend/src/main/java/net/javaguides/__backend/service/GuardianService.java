package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.GuardianDto;
import net.javaguides.__backend.entity.Guardian;

import java.util.List;

public interface GuardianService {

    GuardianDto createGuardian(GuardianDto guardianDto);

    GuardianDto getGuardianById(Long guardianId);

    GuardianDto getGuardianByEmail(String email);

    void deleteGuardian(Long id);

    GuardianDto updateGuardian(Long id, GuardianDto guardianDto);

    List<GuardianDto> getAllGuardians();
}
