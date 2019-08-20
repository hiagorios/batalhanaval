package model.nave;

import model.Square;

public class Destroyer extends Nave {

    public Destroyer() {
        setPartes(2);
        setLetra('D');
    }

    @Override
    public void destroyedPartMessage() {
        System.out.println("Parte de destroyer destruída!");
    }
    
    @Override
    public void destroyedMessage() {
        System.out.println("Destroyer destruído!");
    }
    
    @Override
    public void markGrid(Square[][] grid){
        
    }
}
