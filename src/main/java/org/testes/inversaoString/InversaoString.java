package org.testes.inversaoString;

import java.util.Scanner;

public class InversaoString {
    public static void main(String[] args) {
        System.out.println("Insira uma palavra:");
        Scanner scan = new Scanner(System.in);
        String nome = scan.next();
        char[] letras = nome.toCharArray();

        for (int i = 0; i < letras.length / 2; i++) {
            char temp = letras[i];
            letras[i] = letras[letras.length - 1 - i];
            letras[letras.length - 1 - i] = temp;
        }

        String nomeInvertido = new String(letras);
        System.out.println("Palavra invertida: " + nomeInvertido);
    }
}