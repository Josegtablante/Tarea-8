package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

import static java.util.Arrays.asList;

@Entity
public class Loan { //loan=prestamo

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private Double maxAmount; //cantidadMaxima

    @ElementCollection //indica una relacion simple de uno a muchos entre payments y Loan. esta es una entidad que tiene una sola propiedad y que se relaciona con Loan
    @Column(name = "payments_id")// payments=prestamos
    private List<Integer> payments = new ArrayList<>(); //pago de cuotas. integer es un dato tipo objeto y por eso se le pueden hacer cosas

    @OneToMany(mappedBy="loan",fetch=FetchType.EAGER) //map = asociar
    private Set<ClientLoan> clientLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
}

