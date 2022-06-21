package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
public class UtilsTests {

    @Test
    public void CardNumberIsCreated() {
        String cardNumber = Utils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }

    @Test
    public void existAccountNumber(){
        String accountNumber = Utils.getAccountNumber();
        assertThat(accountNumber,is(lessThanOrEqualTo("VIN 99999")));
    }


}
