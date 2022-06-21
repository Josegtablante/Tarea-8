package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountServices;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Utils.Utils.*;

@RestController

@RequestMapping("/api")  //por defecto los @RequestMapping reciben las peticiones de tipo GET
public class ClientController {
    @Autowired //importo un repositorio al atributo mas cercano
    private ClientServices clientServices; //interface que contiene los metodos de client
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClientsDto() {
        return clientServices.getClientsDto(); // con clientService podemos ahorrar memoria al usar los metodos necesarios y no llamando a JPARepository
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) { //segun que id sea su ruta va a cmabiar y se le pasara por parametros (ruta , variable)
        return clientServices.getClientDTO(id); //map es asociar
    }

//    @PostMapping("/clients")
    @RequestMapping(path = "/clients", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (clientServices.getClientByEmail(email) != null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }
        //contraseña encriptada
        String newpassword = passwordEncoder.encode(password);

        //creo nuevo cliente
        Client cliente = new Client(firstName, lastName, email, newpassword);
        clientServices.saveClient(cliente);

        //defino los num de cuenta y los guardo en una variable tipo Strig
        String number = getAccountNumber();
        //se genera una cuenta al inciar
        Account account1 = new Account(number, 0.00, LocalDateTime.now().plusYears(5), cliente);
        accountServices.getSaveAccount(account1);

        //Metodo para limitar cuentas ....Mejorar
        if (cliente.getAccounts().size() >= 3) {
            return new ResponseEntity<>("no esta permitido tener mas de tres cuentas del mismo tipo", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getClientCurrent(Authentication authentication) {
        return new ClientDTO(clientServices.getClientCurrent(authentication));
    }

}
