/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;


import java.util.ArrayList;

/**
 *
 * @author danie_Daniel Better
 */
public class Board {
    
    private  short boardSize;
    private final short numOfBoardMovementElementsFromSameType;  // This number is the number of Snakes, and the number of Ladders
                                                                 // Overall elements is 2*num
     
    private ArrayList<ArrayList<BoardSlot>> gameBoard; 
    
    public Board(short boardSize, short numOfPlayers, short numOfBoardMovementElements){
        this.boardSize = boardSize;
        this.numOfBoardMovementElementsFromSameType = numOfBoardMovementElements;
        
        
        //http://stackoverflow.com/questions/5533484/java-dynamic-2d-matrix
        gameBoard = new ArrayList<ArrayList<BoardSlot>>();                      // create a new ArrayList (empty) where every cell will be an ArrayList of board slots
        for (int i = 0; i < boardSize; i++){
            gameBoard.add(new ArrayList<BoardSlot>());
        }
         
        // This should be checked during run-time for possible BUGS (index, wrong order and such...)
        // Very importent!!!
        /*
            temp represents the actual "line number" as in the double-dimension array (i.e temp = 0 is the first line in the matrix)
            The inner for-loop builds a new board "line" of slots/cells 
        */
        int temp = 0;
        for (short i = boardSize; i > 0; i--){
            short k = 1;
            for (short j = boardSize; j > 0; j--){
                gameBoard.get(temp).add(new BoardSlot(numOfPlayers, (short) ((i-1)*boardSize+k) ));
                k++;
            }
            temp++;
        }
        
    }

    
    public Board() {
        boardSize = 0;
        numOfBoardMovementElementsFromSameType = 0;
        gameBoard = new ArrayList<ArrayList<BoardSlot>>();   
   }
    
    void changeBoardSlotsStatusAccordingToMovement(short playerNumber, short oldSlotNumber, short newSlotNumber){
        
        BoardSlot tmpBoardSlot; 
        ArrayList<Integer> soldiersAmtArrayList;
        int currPlayersSoldier; 
        
        
        // Decrease the old slot number "soldiers" value by '1', because the current player is "leaving" it
        tmpBoardSlot = getBoardSlot(oldSlotNumber);
        soldiersAmtArrayList = tmpBoardSlot.getSoldiersAmtPerPlayerArrayList();
        currPlayersSoldier = soldiersAmtArrayList.get(playerNumber-1);
        soldiersAmtArrayList.set(playerNumber-1, currPlayersSoldier-1);
        
        // Increase the new slot number "soldiers" value by '1', because the current player is "arriving" to it
        tmpBoardSlot = getBoardSlot(newSlotNumber);
        soldiersAmtArrayList = tmpBoardSlot.getSoldiersAmtPerPlayerArrayList();
        currPlayersSoldier = soldiersAmtArrayList.get(playerNumber-1);
        soldiersAmtArrayList.set(playerNumber-1, currPlayersSoldier+1);
        
    }
    
    public BoardSlot getBoardSlot(short slotNumber){
        
        BoardSlot retval = null;
        
        for (short i = 0; i < boardSize; i++){
            for (short j =0; j < boardSize; j++){
                retval = gameBoard.get(i).get(j); 
                if ( (retval.getSlotNumber() == slotNumber) )
                    return retval; 
                }
            }
        // Should never get here
        return retval; 
    }
    
    public void PlaceMovementElementsOnBoard(BoardMovementElement bme){
        
        BoardCoords tmpCoords = bme.getElementCoords("start");
        short startSlotNumber = (short)(tmpCoords.getBoardYCoord()*(boardSize) + tmpCoords.getBoardXCoord());
        tmpCoords = bme.getElementCoords("end");
        short endSlotNumber = (short)(tmpCoords.getBoardYCoord()*(boardSize) + tmpCoords.getBoardXCoord());
        
        BoardSlot startBoardSlot = getBoardSlot(startSlotNumber);
        startBoardSlot.setMovesTo(endSlotNumber);
        
        if (bme instanceof Ladder){
            startBoardSlot.setSlotType(BoardSlot.Type.LADDER);
        } else if (bme instanceof Snake){
            startBoardSlot.setSlotType(BoardSlot.Type.SNAKE);
        }
        
      
    }
            
    public ArrayList<ArrayList<BoardSlot>> getGameBoard(){
        return this.gameBoard;
    }
    public short getBoardSize(){
        return this.boardSize;
    }
    
    public short getNumberOfMovementElements(){
        return this.numOfBoardMovementElementsFromSameType;
    }
   
    
    
    public void setBoardSize(short boardSize){
        this.boardSize = boardSize;
    }
    
    
}
