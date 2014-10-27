package Modal;

/**
 *
 * @author danie_000
 */
public class Soldier {
    
    private boolean hasFinished;
    private final int id;   // Declearing final here is ok because the one and only time variable will be initalized
                            // on when the constructor is created
    private boolean isActiveSoldier;
    
    private short boardSlot;

    public Soldier(int id) {
        this.hasFinished = false;
        this.id = id;
        this.isActiveSoldier = true;
        this.boardSlot = 1;
    }
    
    public void setSoldierActiveStatusToFalse(){
        this.isActiveSoldier = false; 
        this.hasFinished = true;
    }
    
    public boolean getSoldierActiveStatus(){
        return this.isActiveSoldier;
    }
    
   
    public int getSoldierID(){
        return id;
    }
    
    public void setSoldierFinished(){
        this.hasFinished = true;
        this.isActiveSoldier= false;
    }
    
    public boolean getSoldierGameStatus(){
        return hasFinished;
    }
    
    
    public void setBoardSlot(short boardSlot){
        this.boardSlot = boardSlot;
    } 
    
    public short getBoardSlot(){
        return this.boardSlot;
    }

    void setSoldierActiveStatusToTrue() {
            this.isActiveSoldier = true;
    }
            
}
