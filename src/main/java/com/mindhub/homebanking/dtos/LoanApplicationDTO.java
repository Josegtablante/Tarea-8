package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;

public class LoanApplicationDTO {
    private long id;
    private double amount;
    private Integer payments;
    private String accountNumber;

    //constructores
    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(ClientLoanDTO clientLoanDTO, Transaction transaction) {
        this.id = clientLoanDTO.getId();
        this.amount = clientLoanDTO.getAmounts();
        this.payments = clientLoanDTO.getPayments();
        this.accountNumber = transaction.getAccount().getNumber();
    }

    //Getter and Setter
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public Integer getPayments() {
        return payments;
    }
    public void setPayments(Integer payments) {
        this.payments = payments;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
