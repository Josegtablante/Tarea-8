package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
//un servlet es un objeto java que pertenece a una clase. Encargado de escuchar y responder peticiones.Al pegarle, se ejecuta un método
public class TransactionController {
    @Autowired //inyeccion de dependencia
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private TransactionServices transactionServices;

    /* creacion de cuenta*/
    @Transactional
    //anotacion que controla el envio de transacciones . Para decir que este proceso es transaccional tendríamos que revertir todo lo que se ha hecho si una de las operaciones de reserva de vuelo falla.
    @PostMapping("/transactions") //
    public ResponseEntity<Object> registerTransaction(
            @RequestParam double cantidad, @RequestParam String description,
            @RequestParam String originAccount, @RequestParam String destinationAccount,
            Authentication authentication) {

        Client client = clientServices.getClientCurrent(authentication); //email cliente autenticado
        Account cuentaOrigen = accountServices.findByNumber(originAccount);//cuenta de origen
        Account cuentaDestinatario = accountServices.findByNumber(destinationAccount);//cuenta del destinatario

        //verificaciones
        //por si alguno de los campos esta vacio
        if (cantidad == 0 || description.isEmpty() || cuentaOrigen == null || cuentaDestinatario == null) { //verifico que los campos no esten vacios
            return new ResponseEntity<>("Missing Data, alguno de los campos esta vacio", HttpStatus.FORBIDDEN);
        }
        //que la cuenta de origen no sean las mismas
        if (cuentaOrigen == cuentaDestinatario) {
            return new ResponseEntity<>("La cuenta de origen y destinatario no pueden ser las mismas", HttpStatus.FORBIDDEN);
        }
        //que la cuenta de origen exista
        if (cuentaOrigen == null) {
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.BAD_REQUEST);
        }
        //que la cuenta de origen pertenesca al cliente autentificado
        if (!client.getAccounts().contains(cuentaOrigen)) { //revisar, esta media extraña  cuentaOrigen.getId() == authentication .getId()
            return new ResponseEntity<>("La cuenta no pertenece al cliente auntentificado", HttpStatus.BAD_REQUEST);
        }
        //que exista la cuenta de destino
        if (cuentaDestinatario == null) { //revisar, esta media extraña
            return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.BAD_REQUEST);
        }
        //que la cuenta de origen tenga el monto disponible.
        if (cuentaOrigen.getBalance() < cantidad) {
            return new ResponseEntity<>("La cuenta de origen tiene un saldo menor al que se desea enviar", HttpStatus.BAD_REQUEST);
        }
        if (cantidad <= 0) {
            return new ResponseEntity<>("No puedes ingresar un dato menor a cero", HttpStatus.BAD_REQUEST);
        }

        //A la cuenta de origen se le restará el monto indicado en la petición y a la cuenta de destino se le sumará el mismo monto.
        double balanceDebito = cuentaOrigen.getBalance() - cantidad;
        double balanceCredito = cuentaDestinatario.getBalance() + cantidad;

        cuentaOrigen.setBalance(balanceDebito);
        accountServices.getSaveAccount(cuentaOrigen);

        cuentaDestinatario.setBalance(balanceCredito);
        accountServices.getSaveAccount(cuentaDestinatario);

        // Se deben crear dos transacciones, una con el tipo de transacción “DEBIT” asociada a la cuenta de origen y la otra con el tipo de transacción “CREDIT” asociada a la cuenta de destino.
        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, cantidad, description, LocalDateTime.now().plusDays(5), cuentaOrigen);
        transactionServices.saveTransaction(debitTransaction);
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, cantidad, description, LocalDateTime.now().plusDays(5), cuentaDestinatario);
        transactionServices.saveTransaction(creditTransaction);
        return new ResponseEntity<>("transaccion creada con exito", HttpStatus.CREATED);
    }
}


