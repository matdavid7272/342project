package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto createClient(ClientDto clientDto);

    ClientDto getClientById(Long clientId);

    void deleteClient(Long id);

    ClientDto updateClient(Long id, ClientDto clientDto);

    ClientDto getClientByEmail(String email);

    List<ClientDto> getAllClients();

    Boolean hasBookings(Long clientId);

    void deleteBookingsByClientId(Long clientId);
}
