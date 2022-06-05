package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private AccountRepository account;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    /* creacion de cuenta*/
    @Transactional
    @RequestMapping(path = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> registerTransaction(
            @RequestParam Double cantidad, @RequestParam String description,
            @RequestParam String originAccount, @RequestParam String destinationAccount,
            @RequestParam Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName()); //email cliente autenticado
        Account cuentaOrigen = account.findByNumber(originAccount); //cuenta de origen
        Account cuentaDestinatario = account.findByNumber(destinationAccount); //cuenta del destinatario

        //verificaciones
        //por si alguno de los campos esta vacio
        if (cantidad.toString().isEmpty() || description.isEmpty() || originAccount.isEmpty() || cuentaDestinatario.toString().isEmpty()) { //verifico que los campos no esten vacios
            return new ResponseEntity<>("Missing Data, alguno de los campos esta vacio", HttpStatus.FORBIDDEN);
        }
        //que la cuenta de origen no sean las mismas
        if (cuentaOrigen == cuentaOrigen) {
            return new ResponseEntity<>("La cuenta de origen y destinatario no pueden ser las mismas", HttpStatus.FORBIDDEN);
        }
        //que la cuenta de origen exista
        if (cuentaOrigen == null) {
            return new ResponseEntity<>("La cuenta no existe",HttpStatus.BAD_REQUEST);
        }
        //que la cuenta de origen pertenesca al cliente autentificado
        if (cuentaOrigen.getId() == client.getId()){ //revisar, esta media extraña
            return new ResponseEntity<>("La cuenta no pertenece al cliente auntentificado",HttpStatus.BAD_REQUEST);
        }
        //que exista la cuenta de destino
        if (destinationAccount == destinationAccount){ //revisar, esta media extraña
            return new ResponseEntity<>("La cuenta destino no existe",HttpStatus.BAD_REQUEST);
        }
        //que la cuenta de origen tenga el monto disponible.
        if (cuentaOrigen.getBalance() > cantidad){
            return new ResponseEntity<>("La cuenta de origen tiene un saldo menor al que se desea enviar",HttpStatus.BAD_REQUEST);
        }
        // Se deben crear dos transacciones, una con el tipo de transacción “DEBIT” asociada a la cuenta de origen y la otra con el tipo de transacción “CREDIT” asociada a la cuenta de destino.
        Transaction debitTransaction = transactionRepository.save(new Transaction(TransactionType.DEBITO,cantidad,description, LocalDateTime.now().plusDays(5),cuentaOrigen));
        Transaction creditTransaction = transactionRepository.save(new Transaction(TransactionType.CREDITO,cantidad,description, LocalDateTime.now().plusDays(5),cuentaDestinatario));

        //A la cuenta de origen se le restará el monto indicado en la petición y a la cuenta de destino se le sumará el mismo monto.
        double newcantidad = 0.00;

        double transaccionCredito = cuentaOrigen.getBalance() - cantidad;

        double transaccionDebito = cuentaDestinatario.getBalance() + cantidad;

//        if (cuentaOrigen.getBalance() > cantidad){
//           Double debito = cantidad - cuentaOrigen.getBalance();
//            cuentaOrigen.getBalance() =debito;
//        }

        return new ResponseEntity<>(HttpStatus.CREATED);








    }







}


