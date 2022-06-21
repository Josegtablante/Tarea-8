package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.getAccountNumber;

@RestController
//indica que se va a regir vajo los controladores de rest = REpresentational State Transfer  y seran representados en formato Json. State representa el estado de este momento
@RequestMapping("/api")
public class AccountController { //el controlador escucha peticiones y responde peticiones
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ClientServices clientServices;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccountsDto() {
        return accountServices.getAccountsDto();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountServices.getAccountDto(id);
    }

    /* creacion de cuenta*/
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        Client clienteAutenticado = clientServices.getClientByEmail(authentication.getName());
        String number = getAccountNumber();
        double balance = 0.00;

        if (clienteAutenticado.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Solo se pueden crear 3 cuentas por cliente", HttpStatus.FORBIDDEN);
        }

        Account account1 = new Account(number, balance, LocalDateTime.now().plusYears(5), clienteAutenticado);
        accountServices.getSaveAccount(account1);

        return new ResponseEntity<>("Nueva cuenta creada con exito", HttpStatus.CREATED);
    }


}
