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
    private float m_weight;
    private final char m_column;
    private final char m_row;
    
    Square(char r, char c)
    {
        m_state = SquareState.BLANK;
        m_row = r;
        m_column = c;
    }
    Square(Square s)
    {
        m_state = s.GetState();
        m_row = s.GetRow();
        m_column = s.GetColumn();
    }

    public float getWeight()
    {
        return m_weight;
    }

    public void setWeight(float m_weight)
    {
        this.m_weight = m_weight;
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
                System.out.printf("[WW]");
                break;
            case HIT_DES:
            case HIT_HID:
            case HIT_CRU:
            case HIT_SUB:
            case HIT_PP:
                System.out.printf("[XX]");
                break;
            case IMP:
                System.out.printf("[**]");
                break;
            case BLANK:
            default:
                System.out.printf("[%c%c]", m_row, m_column);
                break;
        }
    }
}
