/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;

/**
 *
 * @author danie_000
 */

/*
    This class will be used to represent a single "cell" or "point" on the board. 
*/
public class BoardCoords {
    private int x;
    private int y;
    
    public BoardCoords(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public BoardCoords(){
        //empty c'tor
    }
    
    public void setBoardCoords(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getBoardXCoord(){
        return this.x;
    }
    
    public int getBoardYCoord(){
        return this.y;
    }
    
}
