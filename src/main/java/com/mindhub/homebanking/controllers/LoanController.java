package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
//anotacion para indicar que esta clase trabaja bajo las restrinciones de rest .usa los servicios de rest en el controlador que escucha y responde peticiones
@RequestMapping("/api") //indica la ruta al json
public class LoanController { //clase publica, tipo class ,nombre de la clase

    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private ClientLoanServices clientLoanServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private LoanServices loanServices;

    @GetMapping("/loan") //asocio una peticion tipo GET desde la ruta /loan
    public List<LoanDTO> getLoansDto() {
        return loanServices.getLoansDto();
    }

    @Transactional
//anotacion que controla el envio de transacciones . Para decir que este proceso es transaccional tendríamos que revertirtodo lo que se ha hecho si una de las operaciones de reserva de vuelo falla.
    @PostMapping("/loans")
// indicacion de la ruta y que sera accesible  bajo el metodo post
    public ResponseEntity<Object> registerLoan(  //ResponseEntity indica la forma en que le devolveremos una respuesta al servidor, <> indican el objeto que va a recibir, puede ser String, puede ser Objet como es en este caso , @RequestBody Pet pet le dice a Spring que analice el cuerpo de la solicitud como un objeto JSON y use ese objeto para crear una instancia de Pet. Se producirá un error si las propiedades del JSON y los valores no coinciden con los campos de loanApplicationDTO  .
                                                 @RequestBody LoanApplicationDTO loanApplicationDTO, //la anotacion RequestBody indica que el parametro debe vincularse al cuerpo de la web, la solicitud se pasa a respuesta en formato httpMessageConverter para resolver el metodo;  La idea es que así como has usado los DTO para enviar la información desde el backend también lo uses para recibirla.
                                                 Authentication authentication) {

        //argumentos
        Account accountNumber = accountServices.findByNumber(loanApplicationDTO.getAccountNumber()); //4 numero de cuenta
        Client client = clientServices.getClientByEmail(authentication.getName()); //email cliente autenticado
        Loan loan = loanServices.findById(loanApplicationDTO.getId()); // 1 ID
        Integer payments = loanApplicationDTO.getPayments(); //3 cuotas
        double amount = loanApplicationDTO.getAmount(); //2 Cantidad
        LocalDateTime date = LocalDateTime.now();

        // Verificar que los datos sean correctos, es decir no estén vacíos, que el monto no sea 0 o que las cuotas no sean 0.
        if (amount == 00.0 || payments == 0 || accountNumber == null) {
            return new ResponseEntity<>("alguno de los campos esta vacio", HttpStatus.FORBIDDEN);
        }
        //que sus montos y cuotas > 0
        if (amount <= 0 || payments <= 0) {
            return new ResponseEntity<>("la cantidad ingresada no puede ser menor a cero", HttpStatus.BAD_REQUEST);
        }
        //Verificar que el préstamo exista
        if (loan == null) {
            return new ResponseEntity<>("el prestamo no existe", HttpStatus.BAD_REQUEST);
        }
        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (amount > loan.getMaxAmount()) {
            return new ResponseEntity<>("el monto solicitado es mayor al prestamo", HttpStatus.BAD_REQUEST);
        }
        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if (!loan.getPayments().contains(payments)) { //mejorar como leer
            return new ResponseEntity<>("la cantidad de cuotas que ingresaste no coinciden con las disponibles", HttpStatus.BAD_REQUEST);
        }
        //Verificar que la cuenta de destino exista
        if (accountNumber == null) {
            return new ResponseEntity<>("la cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }
        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountNumber)) {
            return new ResponseEntity<>("la cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        //Se debe crear una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        double newAmount = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() / 20); //20 % de interes

        // accountNumber.setBalance();<- con esto seteas
        // accountNumber.setBalance(accountNumber.getBalance());<- aca seteas y obtienes el balance que ya tiene la cuenta
        accountNumber.setBalance(accountNumber.getBalance() + loanApplicationDTO.getAmount());//<- aca seteas y obtenes el balance que ya tiene la cuenta y le sumas el monto del loan aplicado
        accountServices.getSaveAccount(accountNumber);

        //debo crear el nuevo loan para luego agregarlo a la transaccion
        ClientLoan newClientLoan = new ClientLoan(newAmount, payments, client, loan);
        clientLoanServices.saveClientLoan(newClientLoan);

        //Se debe crear una transacción “CREDIT” asociada a la cuenta de destino (el monto debe quedar positivo) con la descripción
        // concatenando el nombre del préstamo y la frase “loan approved

        // Se debe crear una transaccion para
        Transaction transaction = new Transaction(TransactionType.CREDIT, amount, loan.getName() + " loan approved", date, accountNumber);
        transactionServices.saveTransaction(transaction);

        return new ResponseEntity<>("loan approved", HttpStatus.CREATED);


    }

}
