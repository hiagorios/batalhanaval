package model;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Random;

public class Tabuleiro 
{

    //Matriz de todas as posicoes
    private Square[][] matriz;
    //Matriz de possibilidades de disparo. Todos os itens da lista sao locais validos para atirar
    private ArrayList<ArrayList<Square>> shotGrid;
    //Matriz de possibilidades para prenchimento. Todos os itens da lista sao locais validos para colocar navios
    private ArrayList<ArrayList<Square>> fillGrid;
    
    @PostConstruct
    /** Initializes the matrix and the shotGrid
     */
    private void initMatriz() 
    {
        matriz = new Square[10][10];
        shotGrid = new ArrayList<ArrayList<Square>>();
        for (int i = 0; i < 10; i++) 
        {
            shotGrid.add(new ArrayList<>());
            for(int j = 0; j < 10; j++)
            {
                char row = (char)('A' + j), column =(char)('0' + i);
                matriz[i][j] = new Square(row, column);
                shotGrid.get(i).add(new Square(matriz[i][j]));
            }
        }
    }
    
    /**
    * @param grid The grid itself
    * @return An empty square that can be used either to place a ship or to be shot
    */
    public static Square EmptySquare(ArrayList<ArrayList<Square>> grid)
    {
         Random rand = new Random();
        int iDex = rand.nextInt(grid.size());
        int jDex = rand.nextInt(grid.get(iDex).size());
        
        return new Square(grid.get(iDex).get(jDex).GetRow(), grid.get(iDex).get(jDex).GetColumn());
    }
    
    /** Generates a random position to shoot and as soon as we upgrade it, it will be able to hunt a ship
     * given an initial hit square
    * @param fireGrid The grid in which the shots will be stored
    * @param showGrid The grid used to visualization
    */
    public static void Shot(ArrayList<ArrayList<Square>> fireGrid, Square[][] showGrid)
    {
        Random rand = new Random();
        int iDex = rand.nextInt(fireGrid.size());
        int jDex = rand.nextInt(fireGrid.get(iDex).size());
        int i = fireGrid.get(iDex).get(jDex).GetRowIndex();     //Actual 'i' of 'matriz'
        int j = fireGrid.get(iDex).get(jDex).GetColumnIndex();  //Actual 'j' of 'matriz'
        
        //Hit checking code will go here
        showGrid[i][j].SetState(Square.SquareState.WATER);
        fireGrid.get(iDex).remove(jDex);
        if(fireGrid.get(iDex).isEmpty())
        {
            fireGrid.remove(iDex);
        }
    }
    
    /** Prints the grid passed as parameter
    *@param grid The grid itself
    */
    public static void PrintGrid(Square[][] grid)
    {
        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                grid[i][j].print();
            }
            System.out.print('\n');
        }
    }
}
