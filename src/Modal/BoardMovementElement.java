/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;

/**
 *
 * @author Daniel Better
 * This class will be an abstract class for 1. Ladder class and 
 *                                          2. Snake class
 * The c'tor of this class will check validation of the given start and end board coordinates, 
 * if the coordinates given are not in the legitimate board area, the c'tor will throw an exception 
 */
abstract public class BoardMovementElement {
    protected BoardCoords start;
    protected BoardCoords end;
    
    public BoardMovementElement (BoardCoords start, BoardCoords end, int boardSize) throws Exception {
        // Check that the start or end points handled are in the legitimate  
        if ( ( start.getBoardXCoord() > boardSize || start.getBoardYCoord() > boardSize ) ||
             ( end.getBoardXCoord() > boardSize || end.getBoardYCoord() > boardSize)) {
            throw new Exception("Exception: coordinates for the Element are not in the allowed dimensions of the board game");
        }
    }
    
    public BoardCoords getElementCoords(String whichCoord){
        BoardCoords retval;
        switch (whichCoord){
            case "start":
                retval = this.start;
                break;
            case "end":
                retval = this.end;
                break;
            default:
                retval = new BoardCoords(-1, -1);   //to signify an error in input
        }
        return retval;     
    }
}
