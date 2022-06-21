package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountServices {

    List<AccountDTO> getAccountsDto();

    AccountDTO getAccountDto(Long id);

    Account findByNumber(String number);

    void getSaveAccount(Account account);
}
