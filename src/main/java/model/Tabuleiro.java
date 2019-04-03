package model;

import javax.annotation.PostConstruct;
import model.nave.Parte;

public class Tabuleiro {

    private Parte[][] matriz; //talvez mudar o tipo pra algo melhorr
    
    @PostConstruct
    private void initMatriz() {
        matriz = new Parte[10][10];
        int i, j;
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 10; j++) {
                matriz[i][j] = new Parte();
            }
        }
    }

    public void printMatriz() {
        for (Parte[] linha : matriz) {
            for (Parte parte : linha) {
                if (parte.getNave() != null) {
                    System.out.print(parte.getNave().getLetra());
                } else {
                    System.out.print("A");
                }
            }
            System.out.println("\n");
        }
    }

    public Integer getIndex(char letra) {
        switch (letra) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            default:
                System.out.println("Letra index inválida");
        }
        return null;
    }
}
