package com.mindhub.homebanking.Utils;

public final class Utils {

    public static int getRandomNumber(int min, int max) { //generador de numeros ramdon usada como herramienta
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int getCVV() {
        return getRandomNumber(1000, 9999);
    }

    public static String getAccountNumber() {
        String newAccountNumber;
        newAccountNumber = "VIN " + getRandomNumber(1000, 9999);
        return newAccountNumber;
    }

    public static String getCardNumber() {
        String CardNumber;
        CardNumber = getRandomNumber(1000, 9999) + " " +
                getRandomNumber(1000, 9999) + " " +
                getRandomNumber(1000, 9999) + " " +
                getRandomNumber(1000, 9999);
        return CardNumber;
    }

}
