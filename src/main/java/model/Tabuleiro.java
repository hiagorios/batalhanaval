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

        return new Square(grid.get(iDex).get(jDex).getRow(), grid.get(iDex).get(jDex).getColumn());
    }
    
    public ArrayList<Square> vizinhos(Square sqs){
        ArrayList<Square> nei = new ArrayList<>();
        boolean left = sqs.getIndexLeft() > 0, right = sqs.getIndexRight() > 0;
        boolean bottom = sqs.getIndexLeft() > 0, top = sqs.getIndexTop() > 0;
        int curC = sqs.getColumnIndex(), curR = sqs.getRowIndex();
        if(top){
            nei.add(matriz[curR - 1][curC]);
        }
        if(left){    
            nei.add(matriz[curR][curC - 1]);
        }
        if(right){
            nei.add(matriz[curR][curC + 1]);
        }
        if(bottom){
            nei.add(matriz[curR + 1][curC]);
        }
        if(bottom && left){
            nei.add(matriz[curR + 1][curC - 1]);
        }
        if(bottom && right){
            nei.add(matriz[curR + 1][curC + 1]);
        }
        if(top && left){
            nei.add(matriz[curR - 1][curC - 1]);
        }
        if(top && right){
            nei.add(matriz[curR - 1][curC + 1]);
        }
        return nei;
    }
    
    /**
     * Mark all squares around a destroyed ship with weigth zero, so we don't fire there again
     * @param showGrid The grid itself
     * @param sqs The last fired shot. Special case if it was a Hidro Viao
     */
    public void markGridZero(Square[][] showGrid, Square sqs){
        int marks = sqs.assumeShipSize();
        ArrayList<Square> nei;
        if(sqs.getState() == Square.SquareState.HIT_HID){
            //Special Case: Search and mark on a 'X' shape
            while(marks > 0){
                nei = vizinhos(sqs);
                for(int i = 0; i < nei.size(); i++){
                    nei.get(i).setWeight(0);
                }
                marks--;
                //Now we need to find the next! Check two spaces to each sideS
            }
        }
        else {
            //Commom Case: Search in a straight line, 'n' times, 'n' dependant of the ship length
        }
    }
    
    /**
     * Updates the probability of the grid, depeding on the success of the current shot
     * @param showGrid The grid itself
     * @param sqs The last fired shot. Special case if it was a Hidro Viao
     * @param destroyed If the ship was destroyed in this shot, this should be true
     */
    public void updateProbs(Square[][] showGrid, Square sqs, boolean destroyed){
        if(destroyed){ //Back to normal firing mode, but before that, mark all squares around AND the ship sunk with 0 weight 
            markGridZero(showGrid, sqs);
        }
        else{
            switch(sqs.getState()){
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
                        showGrid[i][sqs.getColumnIndex()].incrementWeight(-0.1);
                    }
                    for(int i = 0; i < 10; i++) {
                        showGrid[sqs.getRowIndex()][i].incrementWeight(-0.1);
                    }
                    break;
            }
            sqs.setWeight(0);
        }
    }

    /**
     * Makes a shot, depending on the square that have the best probability of having a ship
     * @param fireGrid All possible firing squares (Currently unused)
     * @param showGrid The grid itself
     */
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
        System.out.println("Shotting at " + selectedSquare.getRow() + selectedSquare.getColumn() + "\nResponse[W/C/D/H/S/P]:");
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
        
        fireGrid.get(selectedSquare.getRowIndex()).remove(selectedSquare.getColumnIndex());
        if (fireGrid.get(selectedSquare.getRowIndex()).isEmpty()) {
            fireGrid.remove(selectedSquare.getRowIndex());
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
        int i = fireGrid.get(iDex).get(jDex).getRowIndex();     //Actual 'i' of 'matriz'
        int j = fireGrid.get(iDex).get(jDex).getColumnIndex();  //Actual 'j' of 'matriz'

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
