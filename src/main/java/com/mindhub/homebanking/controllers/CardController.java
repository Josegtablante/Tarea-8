package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.Utils.Utils.getCVV;
import static com.mindhub.homebanking.Utils.Utils.getCardNumber;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private CardServices cardServices;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType type,
                                             @RequestParam CardColor cardColor,
                                             Authentication authentication) {

        //cliente autenticado buscado por email
        Client currentClient = clientServices.getClientByEmail(authentication.getName());
        // nombre completo
        String cardHolder = currentClient.getFirstName() + " " + currentClient.getLastName();
        //numero de tarjeta
        String cardNumber = getCardNumber();
        //cvv
        int cvv = getCVV();

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
        if (cards.size() == 3) {
            return new ResponseEntity<>("no esta permitido tener mas de tres tarjetas del mismo tipo", HttpStatus.FORBIDDEN);
        }

        Card card = new Card(currentClient, cardColor, type, cardHolder, cardNumber, cvv, LocalDateTime.now(), LocalDate.now().plusYears(5));
        cardServices.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


}

