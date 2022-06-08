package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDITO;
import static com.mindhub.homebanking.models.TransactionType.DEBITO;

@SpringBootApplication
public class HomebankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clienteRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository  cardRepository) {
		return (args) -> {

			//CLIENTES
			Client client1 = new Client("Melba","Mora","melbamora@gmail.com", passwordEncoder.encode("asd"));
			clienteRepository.save(client1);

			Client client2 = new Client("Chloe", "O'Brian", "Chloe@gmail.com", passwordEncoder.encode("abcde123"));
			clienteRepository.save(client2);

			Client client3 = new Client("Jose", "Tablante", "jose@gmail.com", passwordEncoder.encode("asd"));
			clienteRepository.save(client3);


            //CUENTAS
			Account account1 = new Account("VIN0001",5000.0,LocalDateTime.now(),client1);
			accountRepository.save(account1);

			Account account2 =new Account("VIN0002",7500.0,LocalDateTime.now().plusDays(1),client1);
			accountRepository.save(account2);

			Account account3 =new Account("VIN0003",37500.0,LocalDateTime.of(2022,3,8,22,19),client2);
			accountRepository.save(account3);

			Account account4 =new Account("VIN0004",47500.0,LocalDateTime.now(),client3);
			accountRepository.save(account4);

			//TRANSACCTION
			Transaction transaccion1 = new Transaction(DEBITO,-2500.0,"internet",LocalDateTime.now(),account1);
			transactionRepository.save(transaccion1);
			Transaction transaccion4 = new Transaction(CREDITO,45000.0,"consola de videojuego",LocalDateTime.of(2022,04,8,04,02,45),account1);
			transactionRepository.save(transaccion4);

			Transaction transaccion2 = new Transaction(CREDITO,1500.0,"Compra de Supermercado",LocalDateTime.now(),account2);
			transactionRepository.save(transaccion2);

			Transaction transaccion3 = new Transaction(CREDITO,5500.0,"Zapatos",LocalDateTime.now().minusHours(54),account2);
			transactionRepository.save(transaccion3);




			//Loan = PRESTAMOS
			List<Integer> cuotas = List.of(12,24,36,48,60);
			Loan prestamo1 = new Loan("Hipotecario",50000.0,cuotas); //12,24,36,48,60
			loanRepository.save(prestamo1);

			List<Integer> cuotas2 = List.of(6,12,24);
			Loan prestamo2 = new Loan("Personal",100000.0,cuotas2); //6,12,24
			loanRepository.save(prestamo2);

			List<Integer> cuotas3 = List.of(6,12,24,36);
			Loan prestamo3 = new Loan("Automotriz",300000.0,cuotas3);//6,12,24,36 //preguntar como agg prestamos
			loanRepository.save(prestamo3);


			//ClientLoan . la union entre client y loan
			ClientLoan clientLoan1 = new ClientLoan(400.000,60,client1,prestamo1);
			clientLoanRepository.save(clientLoan1);
			ClientLoan clientLoan2 = new ClientLoan(50.000,12,client1,prestamo2);
			clientLoanRepository.save(clientLoan2);


			ClientLoan clientLoan3 = new ClientLoan(100.000,24,client2,prestamo2);
			clientLoanRepository.save(clientLoan3);
			ClientLoan clientLoan4 = new ClientLoan(200.000,36,client2,prestamo3);
			clientLoanRepository.save(clientLoan4);

			//CARD
			Card tarjeta1 = new Card(client1,CardColor.GOLD,CardType.CREDIT,account1.getCliente().getFirstName(),"342343423",4321,LocalDateTime.now(), LocalDate.now().plusYears(5));
			cardRepository.save(tarjeta1);

			Card tarjeta2 = new Card(client2,CardColor.SILVER,CardType.CREDIT,account2.getCliente().getFirstName(),"123123123",4332,LocalDateTime.now(), LocalDate.now().plusYears(5));
			cardRepository.save(tarjeta2);

		};
	}




}

