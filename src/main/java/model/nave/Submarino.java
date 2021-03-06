package model.nave;

import model.Square;

public class Submarino extends Nave {

    public Submarino() {
        setPartes(1);
        setLetra('S');
    }
    
    @Override
    public void destroyedPartMessage() {
        System.out.println("Parte de submarino destruída!");
    }
    
    @Override
    public void destroyedMessage() {
        System.out.println("Submarino destruído!");
    }
    
    @Override
    public void markGrid(Square[][] grid){

    }
}
