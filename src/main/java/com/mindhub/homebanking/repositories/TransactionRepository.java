package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource //este repositorio va a trabajar con las restrinciones de rest acceso a los metodos get post delete, path . el arroba indica que es una anotacion
public interface TransactionRepository extends JpaRepository<Transaction, Long> { //trabajamos bajo el concepto de POO, herencia en este caso
}
