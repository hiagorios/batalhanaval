package model;

/**
 * @author Ramon
 */
public class Square {
    public enum SquareState{
        BLANK,      //Nothing on this Square, available to shoot
        FILLED,     //There is a ship piece here
        IMP,        //Impossible. Belongs to a ship's radius
        WATER,      //Shot on water, can't mark again
        HIT_SUB,    //Shot hit a Submarino
        HIT_DES,    //Shot hit a Destroyers
        HIT_CRU,    //Shot hit a Cruzador
        HIT_HID,    //Shot hit a Hidro viao
        HIT_PP,     //Shot hit a Porta Plane
    }
    
    private SquareState m_state;
    private double m_weight;
    private final char m_column;
    private final char m_row;
    
    Square(char r, char c){
        m_state = SquareState.BLANK;
        m_row = r;
        m_column = c;
        m_weight = 0;
    }
    Square(char r, char c, double w){
        m_state = SquareState.BLANK;
        m_row = r;
        m_column = c;
        m_weight = w;
    }
    Square(Square s){
        m_state = s.getState();
        m_row = s.getRow();
        m_column = s.getColumn();
    }

    public double getWeight(){
        return m_weight;
    }

    public void setWeight(double w){
        m_weight = w;
    }
    
    public void incrementWeight(double val){
        m_weight += val;
        if(m_weight < 0){
            m_weight = 0;
        }
    }
    
    void setState(SquareState state){
        m_state = state;
    }   
    SquareState getState(){
        return m_state;
    }
    
    char getRow(){
        return m_row;
    }
    
    int getRowIndex(){
        return m_row - 'A';
    }
    
    char getColumn(){
        return m_column;
    }
    
    boolean isCornerLeft(){
        return m_column == '0';
    }
    
    boolean isCornerRight(){
        return m_column == '9';
    }
    
    boolean isCornerTop(){
        return m_column == 'A';
    }
    
    boolean isCornerBottom(){
        return m_row == 'J';
    }
    
    int getIndexRight(){
        if(isCornerRight()){
            return  -1;
        }
        return getColumnIndex() + 1;
    }
    
    int getIndexLeft(){
        if(isCornerLeft()){
            return  -1;
        }
        return getColumnIndex() - 1;
    }
    
    int getIndexTop(){
        if(isCornerTop()){
            return  -1;
        }
        return getRowIndex() - 1;
    }
    
    int getIndexBottom(){
        if(isCornerBottom()){
            return  -1;
        }
        return getRowIndex() + 1;
    }
    
    int assumeShipSize(){
        switch(m_state){
            case WATER:
            case BLANK:
            case IMP:
            default:
                return 0;
            case HIT_DES:
                return 2;
            case HIT_HID:
                return 3;
            case HIT_CRU:
                return 4;
            case HIT_SUB:
                return 1;
            case HIT_PP:
                return 5;
        }
    }
    
    int getColumnIndex(){
        return m_column - '0';
    }
    
    void print(){
        switch (m_state){
            case WATER:
                System.out.printf("[WWW]");
                break;
            case HIT_DES:
                System.out.printf("[DDD]");
                break;
            case HIT_HID:
                System.out.printf("[HID]");
                break;
            case HIT_CRU:
                System.out.printf("[CRU]");
                break;
            case HIT_SUB:
                System.out.printf("[SUB]");
                break;
            case HIT_PP:
                System.out.printf("[PPA]");
                break;
            case IMP:
                System.out.printf("[***]");
                break;
            case BLANK:
            default:
                System.out.printf("[%.2f]", m_weight);
                break;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.m_column;
        hash = 23 * hash + this.m_row;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Square other = (Square) obj;
        if (this.m_column != other.m_column) {
            return false;
        }
        if (this.m_row != other.m_row) {
            return false;
        }
        return true;
    }
    
    
}
