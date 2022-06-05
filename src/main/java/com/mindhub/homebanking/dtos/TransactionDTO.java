package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionDTO {
    //es una clase donde importo las propiedades(datos) que voy a mostrar

    //Declaro las propiedades que voy a usar
    private Long id;
    private TransactionType type;
    private double cantidad;
    private String descripcion;
    private LocalDateTime date;
    private Set<AccountDTO> accounts = new HashSet<>();

    //constructor vacio
    public TransactionDTO() {
    }

    //constructor lleno
    public TransactionDTO(Transaction transactions) {  //TAREA 3 pag 5
        this.id = transactions.getId();
        this.type = transactions.getType();
        this.cantidad = transactions.getCantidad();
        this.descripcion = transactions.getDescripcion();
        this.date = transactions.getDate();
     //   this.accounts = transactions.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
    }

    //getter and setter
    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
