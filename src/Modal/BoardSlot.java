/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;

import java.util.ArrayList;

/**
 *
 * @author Daniel Better
 * this class is a formal representation of each slot (or CELL if you want to call it like that) 
 * on our game board. 
 * 
 * The relevant information and data members of this class will be just like the board described in the example given
 * 1.
 */



public class BoardSlot {
    
    private ArrayList<Integer> soldiersAmtPerPlayerArrayList;        // This will hold the amount of soldiers each player has

    void setAmountOfSoldierForPlayer(int playerID, int currPlayersSoldiersAmtOnSlot) {

        int amountOfSoldiersForPlayer = soldiersAmtPerPlayerArrayList.get(playerID);
       
        amountOfSoldiersForPlayer=currPlayersSoldiersAmtOnSlot;
        
        
        soldiersAmtPerPlayerArrayList.set(playerID, amountOfSoldiersForPlayer);

    }
                                                                     // we hold it as a dynamic array because we don't know before hand
                                                                     // how many players the game will contain
    public enum Type{
        REG,
        SNAKE,
        LADDER
    }; 
    
    Type slotType; 
    
    private int movesTo; 
    private short slotNumber; 
    
    public BoardSlot(short numOfPlayers, short slotNumber){
        this.slotType = Type.REG;
        this.slotNumber = slotNumber;
        this.soldiersAmtPerPlayerArrayList = new ArrayList();
        int soldiersAmount = 0;
        if (slotNumber == 1){
            soldiersAmount = 4;
        }
        for (int i=0; i < numOfPlayers; i++){
            (this.soldiersAmtPerPlayerArrayList).add(soldiersAmount);               // Initalize the number of soldiers at the slot to '0'
        }
        this.movesTo = 0;
    }
    
    public ArrayList<Integer> getSoldiersAmtPerPlayerArrayList(){
        return this.soldiersAmtPerPlayerArrayList;
    }
    
    public Type getSlotType(){
        return this.slotType;
    }
    
    public short getSlotNumber(){
        return this.slotNumber;
    }
    
    public void setSlotType(Type type){
        this.slotType = type;
    }
    
    public void setMovesTo(int movesTo){
        this.movesTo = movesTo;
    }
    
    public int getMovesTo(){
        return this.movesTo;
    }
    
    public void changeAmountOfSoldierForPlayer(int playerID, char operator)
    {
        int amountOfSoldiersForPlayer = soldiersAmtPerPlayerArrayList.get(playerID);
        
        if(operator == '+')
        {
             amountOfSoldiersForPlayer++;
        }
        else
        {
            amountOfSoldiersForPlayer--;
        }
        
        soldiersAmtPerPlayerArrayList.set(playerID, amountOfSoldiersForPlayer);

    }
    
    public boolean isSlotOccupied(){
        
        // return true if there's atleast one soldier on the slot
        // otherwise, if there are NO soldiers on the slot return false
        for (int i = 0; i <soldiersAmtPerPlayerArrayList.size(); i++){
            if (soldiersAmtPerPlayerArrayList.get(i) != 0){
                return true;
            }
        }
        
        // if we reached here it means no soldier is on the slot
        return false;
        
    }
    
    public void changeAmountOfSoldierForPlayer(int playerID, int newSoldiersAmt){
        soldiersAmtPerPlayerArrayList.set(playerID, newSoldiersAmt);
    }
    
}
