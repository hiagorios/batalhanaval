package model.nave;

public abstract class Nave {
    
    private int partes;
    
    private char letra;
    
    public abstract void destroyedPartMessage();
    
    public abstract void destroyedMessage();

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
