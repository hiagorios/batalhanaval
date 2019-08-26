package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Tabuleiro {

    private int turnCounter = 0;
    private int shipsLeft = 14;
    private Scanner reader;
    //Matriz de todas as posicoes
    private Square[][] matriz;
    //Matriz de possibilidades de disparo. Todos os itens da lista sao locais validos para atirar
    //private ArrayList<ArrayList<Square>> shotGrid;
    //Matriz de possibilidades para prenchimento. Todos os itens da lista sao locais validos para colocar navios
    //private ArrayList<ArrayList<Square>> fillGrid;
    //Lista contendo as partes do navio que está sendo caçado no momento
    private ArrayList<Square> navioCacado;

    public Tabuleiro() {
        reader = new Scanner(System.in);
        matriz = new Square[10][10];
//        shotGrid = new ArrayList<>();
//        fillGrid = new ArrayList<>();
        navioCacado = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
//            shotGrid.add(new ArrayList<>());
//            fillGrid.add(new ArrayList<>());
            for (int j = 0; j < 10; j++) {
                char row = (char) ('A' + i), column = (char) ('0' + j);
                float iVal = 0, jVal = 0;
                //Initialize the grid giving the center more weight than the corners
                if(i <= 4){
                  iVal = (float)(i + 1)  / 10;
                }
                else{
                  iVal = (float)(10 - i)  / 10;  
                }
                if(j <= 4){
                  jVal = (float)(j + 1)  / 10;    
                }
                else{
                  jVal = (float)(10 - j)  / 10;  
                }
                matriz[i][j] = new Square(row, column, iVal + jVal);
//                shotGrid.get(i).add(new Square(matriz[i][j]));
//                fillGrid.get(i).add(new Square(matriz[i][j]));
            }
        }
        while(shipsLeft > 0){
            shotHunt(matriz);
        }
        printMatriz();
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
        //Easter Egg: (spotify:playlist:3wdULViYimfI44KZM4Yjz0)
        return new Square(grid.get(iDex).get(jDex).getRow(), grid.get(iDex).get(jDex).getColumn());
    }
    
    
    public void setSquareLeft(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
        if(!tgt.isCornerLeft() && tgt.getWeight() != 0){
            matriz[tgt.getRowIndex()][tgt.getIndexLeft()].setState(state);
            matriz[tgt.getRowIndex()][tgt.getIndexLeft()].setOrient(orient);
            matriz[tgt.getRowIndex()][tgt.getIndexLeft()].setWeight(weight);
        }
    }
    
    public void setSquareRight(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
        if(!tgt.isCornerRight() && tgt.getWeight() != 0){
            matriz[tgt.getRowIndex()][tgt.getIndexRight()].setState(state);
            matriz[tgt.getRowIndex()][tgt.getIndexRight()].setOrient(orient);
            matriz[tgt.getRowIndex()][tgt.getIndexRight()].setWeight(weight);
        }
    }
    
    public void setSquareTop(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
        if(!tgt.isCornerTop() && tgt.getWeight() != 0){
            matriz[tgt.getIndexTop()][tgt.getColumnIndex()].setState(state);
            matriz[tgt.getIndexTop()][tgt.getColumnIndex()].setOrient(orient);
            matriz[tgt.getIndexTop()][tgt.getColumnIndex()].setWeight(weight);
        }
    }
    
    public void setSquareBottom(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
        if(!tgt.isCornerBottom() && tgt.getWeight() != 0){
            matriz[tgt.getIndexBottom()][tgt.getColumnIndex()].setState(state);
            matriz[tgt.getIndexBottom()][tgt.getColumnIndex()].setOrient(orient);
            matriz[tgt.getIndexBottom()][tgt.getColumnIndex()].setWeight(weight);
        }
    }
    
    public void setSquareTL(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
        if(tgt.haveDiagonalTL() && tgt.getWeight() != 0){
            matriz[tgt.getIndexTop()][tgt.getIndexLeft()].setState(state);
            matriz[tgt.getIndexTop()][tgt.getIndexLeft()].setOrient(orient);
            matriz[tgt.getIndexTop()][tgt.getIndexLeft()].setWeight(weight);
        }
    }
    
    public void setSquareTR(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
         if(tgt.haveDiagonalTR() && tgt.getWeight() != 0){
            matriz[tgt.getIndexTop()][tgt.getIndexRight()].setState(state);
            matriz[tgt.getIndexTop()][tgt.getIndexRight()].setOrient(orient);
            matriz[tgt.getIndexTop()][tgt.getIndexRight()].setWeight(weight);
        }
    }
    
    public void setSquareBL(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
         if(tgt.haveDiagonalBL() && tgt.getWeight() != 0){
            matriz[tgt.getIndexBottom()][tgt.getIndexLeft()].setState(state);
            matriz[tgt.getIndexBottom()][tgt.getIndexLeft()].setOrient(orient);
            matriz[tgt.getIndexBottom()][tgt.getIndexLeft()].setWeight(weight);
        }
    }
    
    public void setSquareBR(Square tgt, Square.SquareState state, Square.ShipOrient orient, double weight){
         if(tgt.haveDiagonalBR() && tgt.getWeight() != 0){
            matriz[tgt.getIndexBottom()][tgt.getIndexRight()].setState(state);
            matriz[tgt.getIndexBottom()][tgt.getIndexRight()].setOrient(orient);
            matriz[tgt.getIndexBottom()][tgt.getIndexRight()].setWeight(weight);
        }
    }

    /**
     * Create and return a list of the neighbors of the passed square
     *
     * @param sqs
     * @param cardinaisOnly
     * @return a list containing the neighbors of sqs
     */
    public ArrayList<Square> vizinhos(Square sqs, boolean cardinaisOnly) {
        ArrayList<Square> nei = new ArrayList<>();
        boolean left = !sqs.isCornerLeft(), right = !sqs.isCornerRight();
        boolean bottom = !sqs.isCornerBottom(), top = !sqs.isCornerTop();
        int curC = sqs.getColumnIndex(), curR = sqs.getRowIndex();
        if (top) {
            nei.add(matriz[curR - 1][curC]);
        }
        if (left) {
            nei.add(matriz[curR][curC - 1]);
        }
        if (right) {
            nei.add(matriz[curR][curC + 1]);
        }
        if (bottom) {
            nei.add(matriz[curR + 1][curC]);
        }
        if (!cardinaisOnly) {
            if (bottom && left) {
                nei.add(matriz[curR + 1][curC - 1]);
            }
            if (bottom && right) {
                nei.add(matriz[curR + 1][curC + 1]);
            }
            if (top && left) {
                nei.add(matriz[curR - 1][curC - 1]);
            }
            if (top && right) {
                nei.add(matriz[curR - 1][curC + 1]);
            }
        }
        return nei;
    }

    /**
     * Mark all squares around a destroyed ship with weight zero, so we don't
     * fire there again
     *
     * @param showGrid The grid itself
     * @param sqs The last fired shot. Special case if it was a Hidro Viao
     */
    public void markGridZero(Square[][] showGrid, Square sqs) {
        ArrayList<Square> neighbors = new ArrayList<>();
        for (Square parte : navioCacado) {
            for (Square nei : vizinhos(parte, false)) {
                if (!neighbors.contains(nei)) {
                    neighbors.add(nei);
                }
            }
        }
        for (Square nei : neighbors) {
            //nei.setWeight(0);
            nei.setState(Square.SquareState.WATER);
        }
        shipsLeft--;
        navioCacado.clear();
    }

    //All Ships:
    //     [0]     |    [1]   OR   [1]   
    //      ?      |     ?               
    //      ?      |     X          W    
    //    ??X??    |     X        ??X??  
    //      ?      |     ?               
    //      ?      |     ?               
    //Hidro-Plane:
    //     [0]   |    [1]   OR   [1]   |   [2]   OR   [2]   OR   [2]
    //    ?   ?  |   ?   X      ?   W  |  X   X      X   W      W   W
    //      X    |     X          X    |    X          X          X
    //    ?   ?  |       ?      ?   ?  |             O          O   O
    // [?] -> Possible | [X] -> Hit | [W] -> Water | [O] -> Next part (100%)
    /**
     * Updates the probability of the grid, depending on the success of the
     * current shot
     *
     * @param showGrid The grid itself
     * @param sqs The last fired shot. Special case if it was a Hidro Viao
     * @param destroyed If the ship was destroyed in this shot, this should be
     * true
     */
    public void updateProbs(Square[][] showGrid, Square sqs, boolean destroyed) {
        if (destroyed) { //Back to normal firing mode, but before that, mark all squares around AND the ship sunk with 0 weight 
            markGridZero(showGrid, sqs);
        } else {
            ArrayList<Square> neighbors = vizinhos(sqs, true);
            switch (sqs.getState()) {
                case HIT_HID:   //Special case
                    if(sqs.haveDiagonalBL()){
                        showGrid[sqs.getIndexBottom()][sqs.getIndexLeft()].setWeight(2);
                    }
                    if(sqs.haveDiagonalBR()){
                        showGrid[sqs.getIndexBottom()][sqs.getIndexRight()].setWeight(2);
                    }
                    if(sqs.haveDiagonalTL()){
                        showGrid[sqs.getIndexTop()][sqs.getIndexLeft()].setWeight(2);
                    }
                    if(sqs.haveDiagonalTR()){
                        showGrid[sqs.getIndexTop()][sqs.getIndexRight()].setWeight(2);
                    }
                    break;
                case WATER:     //Hit water: Do facking nothing
                    for(int i = 0; i < 10; i++){
                        matriz[i][sqs.getColumnIndex()].incrementWeight(-0.01);
                    }
                    for(int j = 0; j < 10; j++){
                        matriz[sqs.getRowIndex()][j].incrementWeight(-0.01);
                    }
                    break;
                default:        //It's not a hidro viao, nor water nor a submarine (because submarines are always fully destroyed)
                    if(navioCacado.size() >= 1){ //If size is greater or equal to 1, we can assume it's orientation
                        int dex = navioCacado.size() - 1;
                        Square lastHit = navioCacado.get(dex);
                        //Check where the last ship was in relation to 'sqs'
                        if(lastHit.isSquareOver(sqs) || lastHit.isSquareUnder(sqs)){
                            sqs.setOrient(Square.ShipOrient.VERT);
                            //Set left and right as zero, since the ship can't be here anyways
                            setSquareLeft(sqs, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            setSquareRight(sqs, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            //If size is one, we need to fix our first wrong guess
                            if(navioCacado.size() == 1){
                                setSquareLeft(lastHit, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                                setSquareRight(lastHit, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            }
                        }
                        else {
                            sqs.setOrient(Square.ShipOrient.HORI);
                            setSquareTop(sqs, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            setSquareBottom(sqs, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            if(navioCacado.size() == 1){
                                setSquareTop(lastHit, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                                setSquareBottom(lastHit, Square.SquareState.WATER, Square.ShipOrient.UNKW, 0);
                            }
                        }
                    }
                    if(sqs.getOrient() == Square.ShipOrient.VERT || sqs.getOrient() == Square.ShipOrient.UNKW) {
                        if(!sqs.isCornerBottom()){
                            showGrid[sqs.getIndexBottom()][sqs.getColumnIndex()].setWeight(2);
                        }
                        if(!sqs.isCornerTop()){
                            showGrid[sqs.getIndexTop()][sqs.getColumnIndex()].setWeight(2);
                        }   
                    }
                    if(sqs.getOrient() == Square.ShipOrient.HORI || sqs.getOrient() == Square.ShipOrient.UNKW) {
                        if(!sqs.isCornerLeft()){
                            showGrid[sqs.getRowIndex()][sqs.getIndexLeft()].setWeight(2);
                        }
                        if(!sqs.isCornerRight()){
                            showGrid[sqs.getRowIndex()][sqs.getIndexRight()].setWeight(2);
                        }
                    }
            }
            sqs.setWeight(0);
        }
    }

    /**
     * Makes a shot, depending on the square that have the best probability of
     * having a ship
     *
     * @param fireGrid All possible firing squares (Currently unused)
     * @param showGrid The grid itself
     */
    public void shotHunt(Square[][] showGrid) {
        ArrayList<Square> probs = new ArrayList<>();
        double maxProb = 0;
        int randShot = 0;
        Square selectedSquare;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (showGrid[i][j].getWeight() > maxProb) {
                    maxProb = showGrid[i][j].getWeight();
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (showGrid[i][j].getWeight() == maxProb) {
                    probs.add(showGrid[i][j]);
                }
            }
        }
        if (probs.size() > 1) {
            randShot = (int) (Math.random() * (probs.size() - 1));
        }
        printMatriz();
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
        switch (in.charAt(0)) {
            case 'W':
                selectedSquare.setState(Square.SquareState.WATER);
                selectedSquare.setWeight(0);
                break;
            case 'C':
                selectedSquare.setState(Square.SquareState.HIT_CRU);
                navioCacado.add(selectedSquare);
                break;
            case 'D':
                selectedSquare.setState(Square.SquareState.HIT_DES);
                navioCacado.add(selectedSquare);
                break;
            case 'H':
                selectedSquare.setState(Square.SquareState.HIT_HID);
                navioCacado.add(selectedSquare);
                break;
            case 'S':
                selectedSquare.setState(Square.SquareState.HIT_SUB);
                navioCacado.add(selectedSquare);
                break;
            case 'P':
                selectedSquare.setState(Square.SquareState.HIT_PP);
                navioCacado.add(selectedSquare);
                break;
        }
        if (in.length() > 1 && in.charAt(1) == 'T') {
            //TODO: Codigo para remover da lista o navio em 'selectedSquare'
            updateProbs(showGrid, selectedSquare, true);
        } else {
            updateProbs(showGrid, selectedSquare, false);
        }

//        fireGrid.get(selectedSquare.getRowIndex()).remove(selectedSquare.getColumnIndex());
//        if (fireGrid.get(selectedSquare.getRowIndex()).isEmpty()) {
//            fireGrid.remove(selectedSquare.getRowIndex());
//        }
    }

    /**
     * Generates a random position to shoot and as soon as we upgrade it, it
     * will be able to hunt a ship given an initial hit square
     *
     * @param fireGrid The grid in which the shots will be stored
     * @param showGrid The grid used to visualization
     */
//    public void shot(ArrayList<ArrayList<Square>> fireGrid, Square[][] showGrid) {
//        Random rand = new Random();
//        int iDex = rand.nextInt(fireGrid.size());
//        int jDex = rand.nextInt(fireGrid.get(iDex).size());
//        int i = fireGrid.get(iDex).get(jDex).getRowIndex();     //Actual 'i' of 'matriz'
//        int j = fireGrid.get(iDex).get(jDex).getColumnIndex();  //Actual 'j' of 'matriz'
//
//        //Hit checking code will go here
//        showGrid[i][j].setState(Square.SquareState.WATER);
//        fireGrid.get(iDex).remove(jDex);
//        if (fireGrid.get(iDex).isEmpty()) {
//            fireGrid.remove(iDex);
//        }
//    }

    /**
     * Prints the grid passed as parameter
     *
     */
    public void printMatriz() {
        System.out.printf("   ");
        for(int i = 0; i < 10; i++){
            System.out.printf("   [%c]  ", '0' + i);
        }
        System.out.printf("\n");
        for (int i = 0; i < 10; i++) {
            System.out.printf("[%c]", 'A' + i);
            for (int j = 0; j < 10; j++) {
                matriz[i][j].print();
            }
            System.out.print('\n');
        }
        System.out.printf("Turn      : %2d\n", ++turnCounter);
        System.out.printf("Ships Left: %2d\n", shipsLeft);
    }

    public Square[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(Square[][] matriz) {
        this.matriz = matriz;
    }

//    public ArrayList<ArrayList<Square>> getShotGrid() {
//        return shotGrid;
//    }
//
//    public void setShotGrid(ArrayList<ArrayList<Square>> shotGrid) {
//        this.shotGrid = shotGrid;
//    }
//
//    public ArrayList<ArrayList<Square>> getFillGrid() {
//        return fillGrid;
//    }
//
//    public void setFillGrid(ArrayList<ArrayList<Square>> fillGrid) {
//        this.fillGrid = fillGrid;
//    }
}
