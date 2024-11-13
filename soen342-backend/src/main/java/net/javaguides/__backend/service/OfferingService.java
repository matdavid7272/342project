package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.OfferingDto;

import java.util.List;

public interface OfferingService {

    OfferingDto createOffering(OfferingDto offeringDto);

    OfferingDto getOfferingById(Long offeringId);

    void deleteOffering(Long id);

    OfferingDto updateOffering(Long id, OfferingDto offeringDto);

    List<OfferingDto> getAllOfferings();

    // Add the method signature for fetching offerings by instructor ID
    List<OfferingDto> getOfferingsByInstructorId(Long instructorId);

    boolean registerInstructorForOffering(Long instructorId, Long offeringId);
}
