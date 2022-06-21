package com.mindhub.homebanking;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//esto es para que se autoconfigure la data para prueba
public class RepositoriesTests {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    //LOANS
    @Test
    public void existLoans() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, is(not(empty()))); // assertThat(asegurarse que) no este vacio
    }

    @Test
    public void existHipotecarioLoan() {
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Hipotecario")))); // asegurarse que exita el credito hipotecario
    }

    /*
        @Test
        public void existPersonalLoan() {
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        @Test
        public void existAutomotrizLoan() {
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Automotriz"))));
        }
    */

    //ACCOUNTS
    @Test
    public void existAccounts() {
        List<Account> accountsList = accountRepository.findAll();
        assertThat(accountsList, is(not(empty())));
    }

    @Test
    public void existAccountNumber() {
        List<Account> accountsList = accountRepository.findAll();
        assertThat(accountsList, hasItem(hasProperty("number", is("VIN0001"))));
    }

    //CLIENTS
    @Test
    public void existClient() {
        Client getClientByEmail = clientRepository.findByEmail("jose@gmail.com");
        assertThat(getClientByEmail, equalTo(getClientByEmail));
    }

    @Test
    public void existClientDtoList() {
        List<Client> client = clientRepository.findAll();
        assertThat(client, is(not(empty())));
    }

    //Cards
    @Test
    public void existCard() {
        List<Card> cardList = cardRepository.findAll();
        assertThat(cardList, is(not(empty())));
    }

    @Test
    public void existClientCard() {
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cliente", is(not(empty())))));
    }

    //Transactions
    @Test
    public void existTransacction() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void CantidadIsEmpty() {
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("cantidad", is(not(empty())))));
    }


}
