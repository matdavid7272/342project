package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    // Map Client entity to ClientDto
    public ClientDto mapToClientDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getLastname(),
                client.getFirstname(),
                client.getEmail(),
                client.getAge());
    }

    // Map ClientDto to Client entity
    public Client mapToClient(ClientDto clientDto) {
        return new Client(
                clientDto.getId(),
                clientDto.getLastname(),
                clientDto.getFirstname(),
                clientDto.getEmail(),
                clientDto.getAge());
    }
}
