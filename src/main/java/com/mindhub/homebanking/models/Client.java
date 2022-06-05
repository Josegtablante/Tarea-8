package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    long id;

    //DECLARO LAS VARIABLES(PROPIEDADES) CON LAS QUE VOY A TRABAJAR
    private String firstName,lastName,email, password;

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER) //map = asociar
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @OneToMany(mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    //CONSTRUCTOR VACIO
    public Client() {
    }

    //CONSTRUCTOR LLENO
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

 //==============================

    // GETTER AND SETTER
    public long getId() {
        return id;
    } //solo el get para poder mostrarlo. no queremos que lo puedan modificar

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccounts (Account account){
        account.setCliente(this);
        accounts.add(account);
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }









}