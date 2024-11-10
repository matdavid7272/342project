package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.entity.Client;
import net.javaguides.__backend.Mapper.ClientMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.ClientRepository;
import net.javaguides.__backend.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = ClientMapper.mapToClient(clientDto);
        Client savedClient = clientRepository.save(client);
        return ClientMapper.mapToClientDto(savedClient);
    }

    @Override
    public ClientDto getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new ResourceNotFoundException("Client with id " + clientId + " does not exist");
        }
        return ClientMapper.mapToClientDto(client);
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id);
        if (client == null) {
            throw new ResourceNotFoundException("Client with id " + id + " does not exist");
        }

        boolean deleted = clientRepository.deleteClient(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Client with id " + id + " could not be deleted");
        }
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto updatedClientDto) {
        Client existingClient = clientRepository.findById(id);
        if (existingClient == null) {
            throw new ResourceNotFoundException("Client with id " + id + " does not exist");
        }

        Client updatedClient = ClientMapper.mapToClient(updatedClientDto);

        // Update client details
        existingClient.setFirstname(updatedClient.getFirstname());
        existingClient.setLastname(updatedClient.getLastname());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setAge(updatedClient.getAge());

        Client savedClient = clientRepository.editClient(id, existingClient);  // Assuming editClient saves and returns updated client
        return ClientMapper.mapToClientDto(savedClient);
    }

    @Override
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(ClientMapper::mapToClientDto)
                .collect(Collectors.toList());
    }
}
