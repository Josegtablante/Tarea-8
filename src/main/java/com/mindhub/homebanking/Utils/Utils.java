package com.mindhub.homebanking.Utils;

public class Utils {

    public static int getRandomNumber(int min, int max) { //generador de numeros ramdon usada como herramienta
        return (int) ((Math.random() * (max - min)) + min);
    }
}
