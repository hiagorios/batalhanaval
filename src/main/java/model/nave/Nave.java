package model.nave;
import model.Square;


public abstract class Nave {
    
    private int partes;
    private char letra;
    //Destroyed state: At 0, we have either vertical or horinzontal. Increment at each hit
    private int desState;
    //All Ships:
    //     [0]     |    [1]   OR   [1]   
    //      ?      |     ?               
    //      ?      |     X          W    
    //    ??X??    |     X        ??X??  
    //      ?      |     ?               
    //      ?      |     ?               
    //Hidro-Plane:
    //     [0]   |    [1]   OR   [1]   |   [2]   OR   [2]   OR   [2]
    //    ?   ?  |   ?   X      ?   W  |  X   X      X   W      W   W
    //      X    |     X          X    |    X          X          X
    //    ?   ?  |       ?      ?   ?  |             O          O   O
    // [?] -> Possible | [X] -> Hit | [W] -> Water | [O] -> Next part (100%)
    
    public abstract void destroyedPartMessage();
    
    public abstract void destroyedMessage();
    
    public abstract void markGrid(Square[][] grid);    //Mark everything with 0 weight AFTER a ship was destroyed

    public int getPartes() {
        return partes;
    }

    public void setPartes(int partes) {
        this.partes = partes;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }
    
}
