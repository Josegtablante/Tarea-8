package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource //transferencia de estado representacional
public interface ClientRepository extends JpaRepository<Client, Long> {

       Client findByEmail(String email); //creo una lista de Clientes donde voy a buscar x su email

}
