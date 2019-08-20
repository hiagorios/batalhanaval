package model;

/**
 * @author Ramon
 */
public class Square
{
    public enum SquareState
    {
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
    
    Square(char r, char c)
    {
        m_state = SquareState.BLANK;
        m_row = r;
        m_column = c;
        m_weight = 0;
    }
    Square(char r, char c, double w)
    {
        m_state = SquareState.BLANK;
        m_row = r;
        m_column = c;
        m_weight = w;
    }
    Square(Square s)
    {
        m_state = s.GetState();
        m_row = s.GetRow();
        m_column = s.GetColumn();
    }

    public double getWeight()
    {
        return m_weight;
    }

    public void setWeight(double w)
    {
        m_weight = w;
    }
    
    public void incrementWeight(double val){
        m_weight += val;
        if(m_weight < 0){
            m_weight = 0;
        }
    }
    
    void setState(SquareState state)
    {
        m_state = state;
    }   
    SquareState GetState()
    {
        return m_state;
    }
    
    char GetRow()
    {
        return m_row;
    }
    
    int GetRowIndex()
    {
        return m_row - 'A';
    }
    
    char GetColumn()
    {
        return m_column;
    }
    
    int GetColumnIndex()
    {
        return m_column - '0';
    }
    
    void print()
    {
        switch (m_state)
        {
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
}
