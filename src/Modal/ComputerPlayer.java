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
public class ComputerPlayer extends Player {
    
      public ComputerPlayer(String name, int id){
        super(name, id);
        isComputer=true;
    }
    
    public ComputerPlayer(String name, int id, short numOfSoldiers){
        super(name, id, numOfSoldiers);
        isComputer=true;
    }
    
    @Override
    public boolean move(short steps, Board gameBoard, short sid){
       
        short gbSize = gameBoard.getBoardSize();
        gbSize = (short)(gbSize*gbSize);
        short currentSoldierStandingPoint;
        short currentSoldierEndingPoint;
        int chosenI = 0;
        
        short distanceBetweenStartingPointAndEndingPoint;
        short maxDistance = 0;
        
        int i;
        BoardSlot.Type slotType;
        BoardSlot b;
        short maxEnd = 0;
        BoardSlot a;
        
        for(i=0; i<soldiersArr.length; i++)
        {
            if(soldiersArr[i].getSoldierActiveStatus()==false)
            {
                continue;
            }
            
            currentSoldierStandingPoint = soldiersArr[i].getBoardSlot();
            a = gameBoard.getBoardSlot((short)(currentSoldierStandingPoint));
            
            
            if(a.getSlotNumber()+steps>=gbSize)
            {
                soldiersArr[i].setSoldierFinished();
                soldiersArr[i].setSoldierActiveStatusToFalse();
                soldiersArr[i].setBoardSlot(gbSize);
                a.changeAmountOfSoldierForPlayer(this.getId(), '-');
                gameBoard.getBoardSlot(gbSize).changeAmountOfSoldierForPlayer(this.getId(), '+');
            
                 return false;
            }
            
            b = gameBoard.getBoardSlot((short)(currentSoldierStandingPoint+steps));
            slotType = b.getSlotType();
            
            if(slotType == BoardSlot.Type.LADDER)
            {
                currentSoldierEndingPoint = (short)b.getMovesTo();
                if(currentSoldierEndingPoint>=gbSize)
               {
                    soldiersArr[i].setSoldierFinished();
                    soldiersArr[i].setSoldierActiveStatusToFalse();
                    soldiersArr[i].setBoardSlot(gbSize);
                    a.changeAmountOfSoldierForPlayer(this.getId(), '-');
                    gameBoard.getBoardSlot(gbSize).changeAmountOfSoldierForPlayer(this.getId(), '+');
                   return false;
               }
            }
            else if(slotType == BoardSlot.Type.SNAKE)
            {
                continue;
            }
            else //(slotType == BoardSlot.Type.REG)
            {
                currentSoldierEndingPoint = b.getSlotNumber();
            }
                
            distanceBetweenStartingPointAndEndingPoint = (short)(currentSoldierEndingPoint-currentSoldierStandingPoint);
                
            if(distanceBetweenStartingPointAndEndingPoint>maxDistance)
            {
                maxDistance=distanceBetweenStartingPointAndEndingPoint;
                maxEnd = currentSoldierEndingPoint;
                chosenI = i;
            }
            else if(distanceBetweenStartingPointAndEndingPoint == maxDistance)
            {
                if(currentSoldierEndingPoint>maxEnd)
                {
                    maxDistance=distanceBetweenStartingPointAndEndingPoint;
                    maxEnd = currentSoldierEndingPoint;
                    chosenI = i;
                }
            }
        }
        
        
        a = gameBoard.getBoardSlot((short)(soldiersArr[chosenI].getBoardSlot()));
        a.changeAmountOfSoldierForPlayer(this.getId(), '-');
        
        b = gameBoard.getBoardSlot(maxEnd);
        b.changeAmountOfSoldierForPlayer(this.getId(), '+');
        soldiersArr[chosenI].setBoardSlot((short)(b.getSlotNumber()));
       
        
       return false;
    }
    
    
}