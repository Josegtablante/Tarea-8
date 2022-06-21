package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    //una clase es un modelo o prototipo que define los atributos y metodos comunes atodo obgeto
    // es un molde de algo que queremos contruir

    //VARIABLES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    long id;

    @Basic
    private String number;
    private Double balance;
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id") //PREGUNTAR
    private Client cliente;

    @OneToMany(mappedBy = "account",fetch = FetchType.EAGER) //PREGUNTAR
    private Set<Transaction> transactions = new HashSet<>(); //tengo una set de propiedades llamadas Transaction de la clase account

    //CONSTRUCTORES
    public Account() {
    }

    public Account(String number, Double balance, LocalDateTime creationDate, Client cliente) {
        this.number = number;
        this.balance = balance;
        this.creationDate = creationDate;
        this.cliente = cliente;
        //capas falte un set de transaction
    }

    //GETTER AND SETTER
    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }
    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

}