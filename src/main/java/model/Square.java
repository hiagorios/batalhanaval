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
    
    public enum ShipOrient{
        UNKW,       //Orientation is unknown
        VERT,       //Ship is vertical
        HORI,       //Ship is horizontal
        HID_R,      //Hidro Viao is right pointed   <
        HID_L,      //Hidro Viao is left pointed    >
        HID_T,      //Hidro Viao is top pointed     ^
        HID_B       //Hidro Viao is bottom pointed  v
    }
    
    private SquareState m_state;
    private ShipOrient m_orient;
    private double m_weight;
    private final char m_column;
    private final char m_row;
    
    Square(char r, char c){
        m_state = SquareState.BLANK;
        m_orient = ShipOrient.UNKW;
        m_row = r;
        m_column = c;
        m_weight = 0;
    }
    Square(char r, char c, double w){
        m_state = SquareState.BLANK;
        m_orient = ShipOrient.UNKW;
        m_row = r;
        m_column = c;
        m_weight = w;
    }
    Square(Square s){
        m_orient = s.getOrient();
        m_state = s.getState();
        m_row = s.getRow();
        m_column = s.getColumn();
    }

    public double getWeight(){
        return m_weight;
    }

    public void setWeight(double w){
        if(m_state == SquareState.BLANK){
            m_weight = w;
        }
    }
    
    public void weightMultipy(double mul){
        if(m_state == SquareState.BLANK){
            m_weight *= mul;
        }
    }
    
    public void incrementWeight(double val){
       if(m_state == SquareState.BLANK){
            m_weight += val;
            if(m_weight < 0){
                m_weight = 0;
            }
       }
    }
    
    public void setState(SquareState state){
        if(m_state == SquareState.BLANK){
            m_state = state;
            m_weight = 0;   
        }
    }   
    public SquareState getState(){
        return m_state;
    }
    
    public void setOrient(ShipOrient orient){
        m_orient = orient;
    }
    public ShipOrient getOrient(){
        return m_orient;
    }
    
    public char getRow(){
        return m_row;
    }
    
    public int getRowIndex(){
        return m_row - 'A';
    }
    
    public char getColumn(){
        return m_column;
    }
    
    public boolean isCornerLeft(){
        return m_column == '0';
    }
    
    public boolean isCornerRight(){
        return m_column == '9';
    }
    
    public boolean isCornerTop(){
        return m_row == 'A';
    }
    
    public boolean isCornerBottom(){
        return m_row == 'J';
    }
    
    public int getIndexRight(){
        if(isCornerRight()){
            return  -1;
        }
        return getColumnIndex() + 1;
    }
    
    public int getIndexLeft(){
        if(isCornerLeft()){
            return  -1;
        }
        return getColumnIndex() - 1;
    }
    
    public int getIndexTop(){
        if(isCornerTop()){
            return  -1;
        }
        return getRowIndex() - 1;
    }
    
    public int getIndexBottom(){
        if(isCornerBottom()){
            return  -1;
        }
        return getRowIndex() + 1;
    }
    
    // !! NO PROTECTION! ONLY CALL THOSE ON SHIP HITS !!
    public boolean isSquareUnder(Square s){
        return getColumnIndex() == (s.getColumnIndex() - 1);
    }
    
    public boolean isSquareOver(Square s){
        return getColumnIndex() == (s.getColumnIndex() + 1);
    }
    
    public boolean isSquareLeft(Square s){
        return getRowIndex() == (s.getRowIndex() + 1);
    }
    
    public boolean isSquareRight(Square s){
        return getRowIndex() == (s.getRowIndex() + 1);
    }
    
    public boolean isSquareTL(Square s){
        return isSquareLeft(s) && isSquareOver(s);
    }
    
    public boolean isSquareTR(Square s){
        return isSquareRight(s) && isSquareOver(s);
    }
    
    public boolean isSquareBL(Square s){
        return isSquareLeft(s) && isSquareUnder(s);
    }
    
    public boolean isSquareBR(Square s){
        return isSquareRight(s) && isSquareUnder(s);
    }
    
    //Top Left
    public boolean haveDiagonalTL() {
        if(isCornerTop() || isCornerLeft()){
            return false;
        }
        return true;
    }
    
    //Top Right
    public boolean haveDiagonalTR() {
        if(isCornerTop() || isCornerRight()){
            return false;
        }
        return true;
    }
    
    //Bottom Left
    public boolean haveDiagonalBL() {
        if(isCornerBottom()|| isCornerLeft()){
            return false;
        }
        return true;
    }
    
    //Bottom Right
    public boolean haveDiagonalBR() {
        if(isCornerTop() || isCornerRight()){
            return false;
        }
        return true;
    }
    
    public int assumeShipSize(){
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
    
    public int getColumnIndex(){
        return m_column - '0';
    }
    
    public void print(){
        System.out.printf("%c%c", m_row, m_column);
        switch (m_state){
            case WATER:
                System.out.printf("[WWWW]");
                break;
            case HIT_DES:
                System.out.printf("[DDDD]");
                break;
            case HIT_HID:
                System.out.printf("[HIDR]");
                break;
            case HIT_CRU:
                System.out.printf("[CRUZ]");
                break;
            case HIT_SUB:
                System.out.printf("[SUBM]");
                break;
            case HIT_PP:
                System.out.printf("[PPAA]");
                break;
            case IMP:
                System.out.printf("[****]");
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
