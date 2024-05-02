package com.example.PruebaTecnica4Agency.repository;

import com.example.PruebaTecnica4Agency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


}
