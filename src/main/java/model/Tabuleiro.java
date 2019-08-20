package model;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tabuleiro {

    private Scanner reader;
    //Matriz de todas as posicoes
    private Square[][] matriz;
    //Matriz de possibilidades de disparo. Todos os itens da lista sao locais validos para atirar
    private ArrayList<ArrayList<Square>> shotGrid;
    //Matriz de possibilidades para prenchimento. Todos os itens da lista sao locais validos para colocar navios
    private ArrayList<ArrayList<Square>> fillGrid;

    public Tabuleiro() {
        reader = new Scanner(System.in);
        matriz = new Square[10][10];
        shotGrid = new ArrayList<>();
        fillGrid = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            shotGrid.add(new ArrayList<>());
            fillGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                char row = (char) ('A' + j), column = (char) ('0' + i);
                //Initialize the grid giving the center more weight than the corners
                matriz[i][j] = new Square(row, column, (float)(5 - Math.abs(i - 4) +  5 - Math.abs(j - 4)) / 10);
                shotGrid.get(i).add(new Square(matriz[i][j]));
                fillGrid.get(i).add(new Square(matriz[i][j]));
            }
        }
    }

    /**
     * Initializes the matrix and the shotGrid
     */
    @PostConstruct
    private void initGrids() {
        
    }

    /**
     * @param grid The grid itself
     * @return An empty square that can be used either to place a ship or to be
     * shot
     */
    public Square emptySquare(ArrayList<ArrayList<Square>> grid) {
        Random rand = new Random();
        int iDex = rand.nextInt(grid.size());
        int jDex = rand.nextInt(grid.get(iDex).size());

        return new Square(grid.get(iDex).get(jDex).GetRow(), grid.get(iDex).get(jDex).GetColumn());
    }
    
    public ArrayList<Square> vizinhos(Square sqs){
        //TODO
        return null;
    }
    
    public void updateProbs(Square[][] showGrid, Square sqs, boolean destroyed){
        if(destroyed){ //Back to normal firing mode, but before that, mark all squares around AND the ship sunk with 0 weight 
            
        }
        else{
            switch(sqs.GetState()){
                case HIT_SUB:
                    break;
                case HIT_HID:
                    break;
                case HIT_CRU:
                    break;
                case HIT_DES:
                    break;
                case HIT_PP:
                    break;
                case WATER: //Hit water: Reduce weight in cardinal directions from this square
                    for(int i = 0; i < 10; i++) {
                        showGrid[i][sqs.GetColumnIndex()].incrementWeight(-0.1);
                    }
                    for(int i = 0; i < 10; i++) {
                        showGrid[sqs.GetRowIndex()][i].incrementWeight(-0.1);
                    }
                    break;
            }
        }
        sqs.setWeight(0);
    }

    public void shotHunt(ArrayList<ArrayList<Square>> fireGrid, Square[][] showGrid){
        ArrayList<Square> probs = new ArrayList<>();
        double maxProb = 0;
        int randShot = 0;
        Square selectedSquare = null;
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(showGrid[i][j].getWeight() > maxProb){
                    maxProb = showGrid[i][j].getWeight();
                }
            }
        }
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(showGrid[i][j].getWeight() == maxProb){
                    probs.add(showGrid[i][j]);
                }
            }
        }
        if(probs.size() > 1){
            randShot = (int)(Math.random() * (probs.size() - 1));
        }
        selectedSquare = probs.get(randShot);
        //W - Water
        //C - Cuzer
        //D - Destroyre
        //H - Hidro viao
        //S - Submaniro
        //P - Porta Avioes
        // !! SE TIVER 'T' NO FINAL: Totalmente destruido !!
        System.out.println("Shotting at " + selectedSquare.GetRow() + selectedSquare.GetColumn() + "\nResponse[W/C/D/H/S/P]:");
        String in = reader.nextLine();
        switch(in.charAt(0)) {
            case 'W':
                selectedSquare.setState(Square.SquareState.WATER);
                break;
            case 'C':
                selectedSquare.setState(Square.SquareState.HIT_CRU);
                break;
            case 'D':
                selectedSquare.setState(Square.SquareState.HIT_DES);
                break;
            case 'H':
                selectedSquare.setState(Square.SquareState.HIT_HID);
                break;
            case 'S':
                selectedSquare.setState(Square.SquareState.HIT_SUB);
                break;
            case 'P':
                selectedSquare.setState(Square.SquareState.HIT_PP);
                break;
        }
        if(in.charAt(1) == 'T') {
            //TODO: Codigo para remover da lista o navio em 'selectedSquare'
            updateProbs(showGrid, selectedSquare, true);
        }
        else {
            updateProbs(showGrid, selectedSquare, false);
        }
        
        fireGrid.get(selectedSquare.GetRowIndex()).remove(selectedSquare.GetColumnIndex());
        if (fireGrid.get(selectedSquare.GetRowIndex()).isEmpty()) {
            fireGrid.remove(selectedSquare.GetRowIndex());
        }
    }
    /**
     * Generates a random position to shoot and as soon as we upgrade it, it
     * will be able to hunt a ship given an initial hit square
     *
     * @param fireGrid The grid in which the shots will be stored
     * @param showGrid The grid used to visualization
     */
    public void shot(ArrayList<ArrayList<Square>> fireGrid, Square[][] showGrid) {
        Random rand = new Random();
        int iDex = rand.nextInt(fireGrid.size());
        int jDex = rand.nextInt(fireGrid.get(iDex).size());
        int i = fireGrid.get(iDex).get(jDex).GetRowIndex();     //Actual 'i' of 'matriz'
        int j = fireGrid.get(iDex).get(jDex).GetColumnIndex();  //Actual 'j' of 'matriz'

        //Hit checking code will go here
        showGrid[i][j].setState(Square.SquareState.WATER);
        fireGrid.get(iDex).remove(jDex);
        if (fireGrid.get(iDex).isEmpty()) {
            fireGrid.remove(iDex);
        }
    }

    /**
     * Prints the grid passed as parameter
     *
     * @param grid The grid itself
     */
    public void printMatriz() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                matriz[i][j].print();
            }
            System.out.print('\n');
        }
    }

    public Square[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Square[][] matriz) {
        this.matriz = matriz;
    }

    public ArrayList<ArrayList<Square>> getShotGrid() {
        return shotGrid;
    }

    public void setShotGrid(ArrayList<ArrayList<Square>> shotGrid) {
        this.shotGrid = shotGrid;
    }

    public ArrayList<ArrayList<Square>> getFillGrid() {
        return fillGrid;
    }

    public void setFillGrid(ArrayList<ArrayList<Square>> fillGrid) {
        this.fillGrid = fillGrid;
    }
}
