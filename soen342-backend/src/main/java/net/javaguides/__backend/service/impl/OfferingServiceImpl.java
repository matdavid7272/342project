package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.entity.Offering;
import net.javaguides.__backend.Mapper.OfferingMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.InstructorRepository;
import net.javaguides.__backend.repository.OfferingRepository;
import net.javaguides.__backend.service.OfferingService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
@AllArgsConstructor
public class OfferingServiceImpl implements OfferingService {

    private final OfferingRepository offeringRepository;
    private final OfferingMapper offeringMapper;
    private final InstructorRepository instructorRepository;

    // ReadWriteLock to manage access to the shared resource
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    @Transactional
    public boolean registerInstructorForOffering(Long instructorId, Long offeringId) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            // Fetch the Instructor
            Optional<Instructor> instructorOptional = instructorRepository.findById(instructorId);
            if (!instructorOptional.isPresent()) {
                throw new RuntimeException("Instructor not found.");
            }
            Instructor instructor = instructorOptional.get();

            // Fetch the Offering
            Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);
            if (!offeringOptional.isPresent()) {
                throw new RuntimeException("Offering not found.");
            }
            Offering offering = offeringOptional.get();

            // Check if the offering is available
            if (!offering.isAvailable()) {
                throw new RuntimeException("Offering is not available.");
            }

            if (offering.getInstructor() != null) {
                return false; // Offering already has an instructor
            }

            // Assign the instructor to the offering
            offering.setInstructor(instructor);
            offering.setAvailable(true);

            // Save the updated Offering entity
            offeringRepository.save(offering);

            return true;
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public OfferingDto createOffering(OfferingDto offeringDto) {
        lock.writeLock().lock();

        try {
            Offering offering = offeringMapper.mapToOffering(offeringDto);
            Offering savedOffering = offeringRepository.save(offering);
            return offeringMapper.mapToOfferingDto(savedOffering);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public OfferingDto getOfferingById(Long offeringId) {
        lock.readLock().lock();

        try {
            Optional<Offering> offeringOptional = offeringRepository.findById(offeringId);
            if (!offeringOptional.isPresent()) {
                throw new ResourceNotFoundException("Offering with id " + offeringId + " does not exist");
            }
            Offering offering = offeringOptional.get();
            return offeringMapper.mapToOfferingDto(offering);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void deleteOffering(Long id) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            Optional<Offering> offeringOptional = offeringRepository.findById(id);
            if (!offeringOptional.isPresent()) {
                throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
            }
            offeringRepository.delete(offeringOptional.get());
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public OfferingDto updateOffering(Long id, OfferingDto updatedOfferingDto) {
        lock.writeLock().lock(); // Acquire the write lock

        try {
            Optional<Offering> existingOfferingOptional = offeringRepository.findById(id);
            if (!existingOfferingOptional.isPresent()) {
                throw new ResourceNotFoundException("Offering with id " + id + " does not exist");
            }

            Offering updatedOffering = offeringMapper.mapToOffering(updatedOfferingDto);
            updatedOffering.setId(id);

            Offering savedOffering = offeringRepository.save(updatedOffering);
            return offeringMapper.mapToOfferingDto(savedOffering);
        } finally {
            lock.writeLock().unlock(); // Release the write lock
        }
    }

    @Override
    public List<OfferingDto> getAllOfferings() {
        lock.readLock().lock(); // Acquire the read lock

        try {
            List<Offering> offerings = offeringRepository.findAll();
            return offerings.stream()
                    .map(offeringMapper::mapToOfferingDto)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }

    @Override
    public List<OfferingDto> getOfferingsByInstructorId(Long instructorId) {
        lock.readLock().lock(); // Acquire the read lock

        try {
            List<Offering> offerings = offeringRepository.findByInstructorId(instructorId);
            return offerings.stream()
                    .map(offeringMapper::mapToOfferingDto)
                    .collect(Collectors.toList());
        } finally {
            lock.readLock().unlock(); // Release the read lock
        }
    }
}
