package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.ClientDto;
import net.javaguides.__backend.entity.Client;

public class ClientMapper {

    // Map Client entity to ClientDto
    public static ClientDto mapToClientDto(Client client) {
        return new ClientDto(
                client.getId(),
                client.getLastname(),
                client.getFirstname(),
                client.getEmail(),
                client.getAge()
        );
    }

    // Map ClientDto to Client entity
    public static Client mapToClient(ClientDto clientDto) {
        return new Client(
                clientDto.getId(),
                clientDto.getLastname(),
                clientDto.getFirstname(),
                clientDto.getEmail(),
                clientDto.getAge()
        );
    }
}
