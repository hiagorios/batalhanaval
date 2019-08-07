package runner;

import model.Tabuleiro;

public class BattleShips {

    // initial configuration goes here
    public static void main(String[] args) {
        BattleShips game = new BattleShips();
        Tabuleiro tabuleiro = new Tabuleiro();
        game.distribuirNavios(tabuleiro);
        // verificar quem comeca atirando
        game.run();
    }

    public void distribuirNavios(Tabuleiro tabuleiro) {
        
    }

    // main execution goes here
    public void run() {

    }
}
