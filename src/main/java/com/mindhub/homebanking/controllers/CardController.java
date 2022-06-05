package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utils.Utils.getRandomNumber;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientRepository clienteRepository;
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(@RequestParam CardType type,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication){

        //cliente autenticado buscado por email
        Client currentClient = clienteRepository.findByEmail(authentication.getName());
        // nombre completo
        String cardHolder = currentClient.getFirstName() +" "+ currentClient.getLastName();
        //numero de tarjeta
        String cardNumber = getRandomNumber(1000,9999)+" "+ getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999)+" "+getRandomNumber(1000,9999);
        //cvv
        int cvv = getRandomNumber(100,999);

        //valido que solo se puedan crear 3 tarjetas por persona
/*        if (type == CardType.DEBIT){
            return new ResponseEntity<>("no esta permitido tener mas de tres tarjetas del mismo tipo", HttpStatus.FORBIDDEN);
        }
        if (type == CardType.CREDIT){
            return new ResponseEntity<>("no esta permitido tener mas de tres tarjetas del mismo tipo", HttpStatus.FORBIDDEN);
        }

 */
        //primera forma de hacerlo
        Set<Card> cards = currentClient.getCards().stream().filter(card -> card.getType() == type).collect(Collectors.toSet());
       if (cards.size() == 3){
            return new ResponseEntity<>("no esta permitido tener mas de tres tarjetas del mismo tipo", HttpStatus.FORBIDDEN);
        };

       cardRepository.save(new Card(currentClient,cardColor,type, cardHolder,cardNumber,cvv, LocalDateTime.now(),LocalDate.now().plusYears(5)));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}

