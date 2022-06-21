package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity //es una anotacion , anota lo primero que tenga abajo
public class Transaction {

    @Id //es una anotacion que genera una llave unica
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    //va a decir que el Id es la clave primaria de transacction
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private TransactionType type;
    private double cantidad;
    private String descripcion;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id") //PREGUNTAR
    private Account account; //propiedad de tipo account, de account, privada para acceder con los getter andd setter

    //contructor vacio
    public Transaction() {
    }

    //constructor lleno
    public Transaction(TransactionType type, double cantidad, String descripcion, LocalDateTime date, Account account) { //a la propiedad del objeto que quiero construir que en este caso es de tipo transaccion le estoy pasando un parametro y que tome el valor de ese parametro
        this.type = type;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.date = date;
        this.account = account;
    }

    //Getter And Setter


    public Long getId() { //metodo accesor que te permite acceder a los datos ya sea para traerlos como para modificarlos
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}