/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;

/**
 *
 * @author Daniel Better
 */
public class HumanPlayer extends Player {
    
    public HumanPlayer(String name, int id){
        super(name, id);
        isComputer = false;
    }
    
    public HumanPlayer(String name, int id, short numOfSoldiers){
        super(name, id, numOfSoldiers);
        isComputer = false;
    }
    
    /*
        This method logical functioanlity is to "play" a players TURN.
        The sequence flow leading to the use of this function is the following:
        1. UI methods recieve an input from the current player. 
           This inputs contain 1. Which soldier he wants to move (soldierID)
                               2. 
    */
    
    @Override
    public boolean  move(short steps,  Board gameBoard, short soldierID){
       // Sanity checks
       // Remebmer!!! soldierID is a short number between 1-4 (that's how a human thinks!) but in our soldier's array, we 
       // start from index 0, so when iterating through soldierArr, regard the index as [given ID minus 1]
                

        BoardSlot slotBeforeMovement;
        BoardSlot slotAfterMovement;
        BoardSlot.Type slotType;
        int slotNumberOfMovesTo;
        short slotNumberAfterMovement;
        short gbSize = gameBoard.getBoardSize();
        gbSize = (short)(gbSize*gbSize);
        
        slotBeforeMovement = gameBoard.getBoardSlot(this.getSoldier(soldierID).getBoardSlot());
        slotBeforeMovement.changeAmountOfSoldierForPlayer(this.getId(), '-');
       
        int boardSize = gameBoard.getBoardSize();
        
        Soldier currSoldier = soldiersArr[soldierID];
        short currentBoardSlotNum = currSoldier.getBoardSlot();
        int finishBoardSlot = boardSize * boardSize;
        if ((int)currentBoardSlotNum + steps >= finishBoardSlot){
            currSoldier.setBoardSlot((short)finishBoardSlot);
        } else{
            currSoldier.setBoardSlot( (short) (currSoldier.getBoardSlot()+steps));
        }
        
        
        slotNumberAfterMovement = (this.getSoldier(soldierID)).getBoardSlot();
        slotAfterMovement = gameBoard.getBoardSlot(slotNumberAfterMovement);
        
        slotType = slotAfterMovement.getSlotType();
        if (slotType != BoardSlot.Type.REG) {
            slotNumberOfMovesTo = slotAfterMovement.getMovesTo();            
            this.getSoldier(soldierID).setBoardSlot((short)slotNumberOfMovesTo);
            slotAfterMovement = gameBoard.getBoardSlot((short) slotNumberOfMovesTo);
        }
        
        // Check if we reached or passed the last board slot (finish slot)
        if (slotNumberAfterMovement >= gbSize){
            this.getSoldier(soldierID).setSoldierFinished();
            this.getSoldier(soldierID).setSoldierActiveStatusToFalse();
            this.getSoldier(soldierID).setBoardSlot(gbSize);
            slotAfterMovement = gameBoard.getBoardSlot(gbSize);
            slotAfterMovement.changeAmountOfSoldierForPlayer(this.getId(), '+');
            
            return false;
        }
        
        slotAfterMovement.changeAmountOfSoldierForPlayer(this.getId(), '+');
        return false;
    }
    
    
    
}
