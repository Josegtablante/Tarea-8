package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utils.Utils.getRandomNumber;

@RestController //indica que se va a regir vajo los controladores de rest = REpresentational State Transfer  y seran representados en formato Json. State representa el estado de este momento
@RequestMapping("/api")
public class AccountController { //el controlador escucha peticiones y responde peticiones
    @Autowired
    private AccountRepository account;
    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return account.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return account.findById(id).map(AccountDTO::new).orElse(null);
    }

    /* creacion de cuenta*/

    @RequestMapping(path = "/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        Client clienteAutenticado = clientRepository.findByEmail(authentication.getName());
        String number = "VIN"+getRandomNumber(1000,9999);
        double balance = 0.00;

        if (clienteAutenticado.getAccounts().size() >= 3 ){
            return new ResponseEntity<>("Solo se pueden crear 3 cuentas por cliente", HttpStatus.FORBIDDEN);
        }

        Account account1 = account.save(new Account(number,balance, LocalDateTime.now().plusYears(5),clienteAutenticado));
        return new ResponseEntity<>("Nueva cuenta creada con exito", HttpStatus.CREATED);
    }



}
