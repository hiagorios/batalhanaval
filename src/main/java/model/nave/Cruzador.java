package model.nave;

public class Cruzador extends Nave {

    public Cruzador() {
        setPartes(4);
        setLetra('C');
    }

    @Override
    public void destroyedPartMessage() {
        System.out.println("Parte de cruzador destruída!");
    }

    @Override
    public void destroyedMessage() {
        System.out.println("Cruzador destruído!");
    }
}
