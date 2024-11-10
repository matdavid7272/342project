package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Offering;

import java.util.List;

public interface OfferingService {

    OfferingDto createOffering(OfferingDto offeringDto);

    OfferingDto getOfferingById(Long offeringId);

    void deleteOffering(Long id);

    OfferingDto updateOffering(Long id, OfferingDto offeringDto);

    List<OfferingDto> getAllOfferings();

}
