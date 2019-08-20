package model.nave;

import model.Square;

public class PortaAvioes extends Nave {

    public PortaAvioes() {
        setPartes(5);
        setLetra('P');
    }
    
    
    @Override
    public void destroyedPartMessage() {
        System.out.println("Parte de porta-aviões destruída!");
    }

    @Override
    public void destroyedMessage() {
        System.out.println("Porta-aviões destruído!");
    }
    
    @Override
    public void markGrid(Square[][] grid){

    }
}
