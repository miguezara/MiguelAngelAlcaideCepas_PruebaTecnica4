package com.example.PruebaTecnica4Agency.controller;

import com.example.PruebaTecnica4Agency.DTO.ClientDTO;
import com.example.PruebaTecnica4Agency.model.Client;
import com.example.PruebaTecnica4Agency.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping
    public ResponseEntity<Client> saveClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        HttpStatus status = (savedClient != null) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(status).body(savedClient);
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAllClient();
        return ResponseEntity.ok().body(clients);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Client> getClientById(@PathVariable String dni) {
        Client client = clientService.findClientById(dni);
        HttpStatus status = (client != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(client);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Client> updateClient(@PathVariable String dni, @RequestBody ClientDTO updatedClientData) {
        Client updatedClient = clientService.updateClient(dni, updatedClientData);
        HttpStatus status = (updatedClient != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(updatedClient);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> deleteClient(@PathVariable String dni) {
        Client deletedClient = clientService.deleteClient(dni);
        HttpStatus status = (deletedClient != null) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).build();
    }
}

