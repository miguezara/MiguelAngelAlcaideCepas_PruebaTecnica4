package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.ClientDTO;
import com.example.PruebaTecnica4Agency.model.Client;
import com.example.PruebaTecnica4Agency.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService implements IClientService {

    private final ClientRepository clientRepository;

    // Método para guardar un nuevo cliente
    @Override
    public Client saveClient(Client client) {
        // Verificar si el cliente ya existe
        if (existClient(client.getDni())) {
            throw new IllegalArgumentException("Client with DNI " + client.getDni() + " already exists.");
        }

        // Agregar el cliente a las reservas de habitación asociadas
        client.getRoomBookings().forEach(roomBooking -> roomBooking.getClients().add(client));
        // Agregar el cliente a las reservas de vuelo asociadas
        client.getFlightBookings().forEach(flightBooking -> flightBooking.getClients().add(client));

        return clientRepository.save(client); // Guardar el nuevo cliente en el repositorio
    }

    // Método para obtener todos los clientes
    @Override
    public List<Client> findAllClient() {
        return clientRepository.findAll();
    }

    // Método para encontrar un cliente por su DNI
    @Override
    public Client findClientById(String dni) {
        try {
            return clientRepository.findById(Long.valueOf(dni)).orElse(null);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DNI format: " + dni);
        }
    }

    // Método para actualizar un cliente
    @Override
    public Client updateClient(String dniClient, ClientDTO updateDto) {
        try {
            Client existingClient = clientRepository.findById(Long.valueOf(dniClient)).orElse(null);

            if (existingClient != null) {
                // Actualizar los atributos del cliente con los valores del DTO
                existingClient.setName(updateDto.getName());
                existingClient.setLastName(updateDto.getLastName());
                existingClient.setEmail(updateDto.getEmail());
                return clientRepository.save(existingClient); // Guardar y devolver el cliente actualizado
            }
            return null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DNI format: " + dniClient);
        }
    }

    // Método para actualizar un cliente
    @Override
    public Client updateClient(String dniClient, Client updatedClient) {
        try {
            Client existingClient = clientRepository.findById(Long.valueOf(dniClient)).orElse(null);

            if (existingClient != null) {
                // Actualizar los atributos del cliente con los valores del DTO
                existingClient.setName(updatedClient.getName());
                existingClient.setLastName(updatedClient.getLastName());
                existingClient.setEmail(updatedClient.getEmail());
                return clientRepository.save(existingClient); // Guardar y devolver el cliente actualizado
            }
            return null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DNI format: " + dniClient);
        }
    }


    // Método para eliminar un cliente
    @Override
    public Client deleteClient(String dni) {
        try {
            if (existClient(dni)) {
                // Eliminar el cliente si existe
                return clientRepository.findById(Long.valueOf(dni))
                        .map(client -> {
                            clientRepository.delete(client);
                            return client;
                        })
                        .orElse(null);
            }
            return null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DNI format: " + dni);
        }
    }

    // Método auxiliar para verificar si un cliente existe con el DNI dado
    private boolean existClient(String dni) {
        try {
            return clientRepository.existsById(Long.valueOf(dni));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid DNI format: " + dni);
        }
    }
}
