package com.example.PruebaTecnica4Agency.service;

import com.example.PruebaTecnica4Agency.DTO.ClientDTO;
import com.example.PruebaTecnica4Agency.model.Client;

import java.util.List;

public interface IClientService {


    Client saveClient(Client client);

    List<Client> findAllClient();

    Client findClientById(String dni);

    Client updateClient(String dniPClient, ClientDTO ClientUpdateDTO);


    Client updateClient(String dniClient, Client ClientDTO);

    Client deleteClient(String dni);
}

