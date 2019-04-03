package model.nave;

public class Hidroaviao extends Nave {

    public Hidroaviao() {
        setPartes(3);
        setLetra('H');
    }
    
    @Override
    public void destroyedPartMessage() {
        System.out.println("Parte de hidroavião destruída!");
    }
    
    @Override
    public void destroyedMessage() {
        System.out.println("Hidroavião destruído!");
    }
}
