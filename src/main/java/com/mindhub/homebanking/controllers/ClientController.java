package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
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

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired //importo un repositorio al atributo mas cercano
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @RequestMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) { //segun que id sea su ruta va a cmabiar y se le pasara por parametros (ruta , variable)
        return clientRepository.findById(id).map(cliente -> new ClientDTO(cliente)).orElse(null);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        //contraseña encriptada
        String newpassword = passwordEncoder.encode(password);

        //creo nuevo cliente
        Client cliente = new Client(firstName, lastName, email, newpassword);
        clientRepository.save(cliente);

        //numero de cuenta + " "+getRandomNumber(1000,9999)+ " "+getRandomNumber(1000,9999)+ " "+getRandomNumber(1000,9999)
        String number = "VIN " + getRandomNumber(1000,9999);
        //se genera una cuenta al inciar
        Account account1 = new Account(number,0.00, LocalDateTime.now().plusYears(5),cliente);
        accountRepository.save(account1);

        //Metodo para limitar cuentas ....Mejorar
        if (cliente.getAccounts().size() >= 3){
            return new ResponseEntity<>("no esta permitido tener mas de tres cuentas del mismo tipo", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
   }

   @RequestMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication){
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

}
