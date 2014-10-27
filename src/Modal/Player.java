package Modal;

/**
 *
 * @author Daniel Better
 * This class is in abstract class for the games "Player". 
 * At this point two Children will inherit from Player: 1. HumanPlayer class
 *                                                      2. ComputerPlayer class
 */

abstract public class Player {
    protected String name;
    protected Soldier[] soldiersArr;
    protected boolean isActivelyPlaying; 
    protected  int id; 
    protected boolean  isComputer;
    
   public Player(String name, int id){
       this.name = name;
       this.isActivelyPlaying = true;
       
       soldiersArr = new Soldier[GameConstant.NumberOfSoldiersPerPlayer];
       for (int soldierIndex = 0; soldierIndex < GameConstant.NumberOfSoldiersPerPlayer; soldierIndex++){
           soldiersArr[soldierIndex] = new Soldier(soldierIndex+1);
       }
       this.id = id;
    }
    
    public Player(String name, int id, short numOfSoldiers){
       this.name = name;
       this.isActivelyPlaying = true;
       
       soldiersArr = new Soldier[numOfSoldiers];
       for (int soldierIndex = 0; soldierIndex < numOfSoldiers; soldierIndex++){
           soldiersArr[soldierIndex] = new Soldier(soldierIndex+1);
       }
       this.id = id;
    }
    
    public String getPlayerName(){
        return this.name;
    }
    
    public boolean hasPlayerWon(short numOfSoldiersNeededToWin)
    {
        int currentFinishedSoldiers = 0;
        boolean hasPlayerWon = false;
        
        for(int i=0; i <this.soldiersArr.length; i++){
            if((this.soldiersArr[i].getSoldierGameStatus())&&( currentFinishedSoldiers < numOfSoldiersNeededToWin))
            {
                currentFinishedSoldiers++;
            }
            if (currentFinishedSoldiers >= numOfSoldiersNeededToWin)
            {
                hasPlayerWon = true;
                break;
            }
        }
        
        return hasPlayerWon;
    }
    
    public void setPlayingStatusToFalse(){
        this.isActivelyPlaying = false;
        
        for (int soldierIndex = 0; soldierIndex < GameConstant.NumberOfSoldiersPerPlayer; soldierIndex++){
           soldiersArr[soldierIndex].setSoldierActiveStatusToFalse();
        }
    }
    
    public boolean getPlayingStatus(){
        return this.isActivelyPlaying;
    }
    
    public boolean isComputerPlay()
    {
        return this.isComputer;
    }
    
    //the return boolean of move will indicate if the game ended or not
    abstract public boolean move(short steps, Board manger, short sid); // Children should implement this method
    
    public Soldier getSoldier(short soldierID){
        
        // Assumption - the caller of this method will send soldierID at the correct indecies
        // i.e - from 0-3
        return soldiersArr[soldierID];
        
    }
    
    public Soldier[] getAllSoldiers(){
        return soldiersArr;
    }
    

    public int getId(){
        return this.id;
        
    }
    

    public void setId(int id){
        this.id = id;
    }
}
