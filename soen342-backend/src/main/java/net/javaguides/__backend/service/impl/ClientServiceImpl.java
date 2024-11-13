package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.entity.Client;
import net.javaguides.__backend.Mapper.ClientMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.BookingRepository;
import net.javaguides.__backend.repository.ClientRepository;
import net.javaguides.__backend.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper; // Inject ClientMapper
    private final BookingRepository bookingRepository;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        // Check if a client with the same email already exists
        Optional<Client> existingClient = clientRepository.findByEmail(clientDto.getEmail());
        if (existingClient.isPresent()) {
            // If email exists, throw a custom exception
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert ClientDto to Client entity using injected ClientMapper
        Client client = clientMapper.mapToClient(clientDto);

        // Save the client entity
        Client savedClient = clientRepository.save(client);

        // Convert saved client back to DTO
        return clientMapper.mapToClientDto(savedClient);
    }

    @Override
    public ClientDto getClientById(Long clientId) {
        // Try to find the client by ID
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (!clientOptional.isPresent()) {
            // Handle case where client is not found
            throw new ResourceNotFoundException("Client with id " + clientId + " does not exist");
        }
        Client client = clientOptional.get();
        return clientMapper.mapToClientDto(client);
    }

    @Override
    public void deleteClient(Long id) {
        // Try to find the client by ID
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            // Handle case where client is not found
            throw new ResourceNotFoundException("Client with id " + id + " does not exist");
        }
        // Delete the found client
        clientRepository.delete(clientOptional.get());
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto updatedClientDto) {
        // Try to find the client by ID
        Optional<Client> existingClientOptional = clientRepository.findById(id);
        if (!existingClientOptional.isPresent()) {
            throw new ResourceNotFoundException("Client with id " + id + " does not exist");
        }

        // Check if another client with the same email exists (excluding the current
        // one)
        Optional<Client> clientWithSameEmail = clientRepository.findByEmail(updatedClientDto.getEmail());
        if (clientWithSameEmail.isPresent() && !clientWithSameEmail.get().getId().equals(id)) {
            throw new ResourceNotFoundException("Email Duplicate");
        }

        // Convert updated DTO to entity using injected ClientMapper
        Client updatedClient = clientMapper.mapToClient(updatedClientDto);
        updatedClient.setId(id);

        // Save the updated client
        Client savedClient = clientRepository.save(updatedClient);
        return clientMapper.mapToClientDto(savedClient);
    }

    @Override
    public List<ClientDto> getAllClients() {
        // Get all clients from the repository
        List<Client> clients = clientRepository.findAll();
        // Convert clients to DTOs and return as a list
        return clients.stream()
                .map(clientMapper::mapToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto getClientByEmail(String email) {
        // Retrieve the client by email using clientRepository
        Optional<Client> clientOptional = clientRepository.findByEmail(email);

        // If the client is not found, throw a custom exception
        if (!clientOptional.isPresent()) {
            throw new ResourceNotFoundException("Client with email " + email + " not found");
        }

        // Convert the found client entity to ClientDto and return it
        return clientMapper.mapToClientDto(clientOptional.get());
    }

    @Override
    public Boolean hasBookings(Long clientId) {
        return bookingRepository.existsByClientId(clientId);
    }

    @Override
    @Transactional
    public void deleteBookingsByClientId(Long clientId) {
        bookingRepository.deleteByClientId(clientId);
    }
}
