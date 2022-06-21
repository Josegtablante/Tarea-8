package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanServices {

    List<LoanDTO> getLoansDto();

    Loan findById(long id);
}
