/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLUtils;

/**
 *
 * @author danie_000
 */
public class XMLExceptions extends Exception{
    
    
    public XMLExceptions(String msg){
        super(msg);
    }
    
    public static void throwNamesError() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: Must contain different uniqe player names.");
        throw e;
    }
    
    public static void throwLadderError() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: Ladder cannot be put on the first (starting) board slot.");
        throw e;
    }
    
    public static void throwSnakeError() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: snake cannot be located on the last board slot.");
        throw e;
    }
    
    public static void throwNumOfBmeDoesntMatch() throws XMLExceptions{
        
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: number of board movement elements of type snake doesn't equal the number of board element of type ladder.");
        throw e;
    }
    
    public static void throwSnakeAndLadderOnSameSlot() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: a ladder and a snake cannot be placed on the same slot.");
        throw e;
    }
    
    public static void throwPlayerNamesDontMeetSoldierOwnerNames() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: The name of each of the soldier's owner, must be one of the name of the active players.");
        throw e;
    }
    
    public static void throwNonMatchingSoldiersAmtPerPlayer() throws XMLExceptions{
        XMLExceptions e = new XMLExceptions("The xml file did not meet the following: the number of soldiers each player contains should be equal!");
        throw e;
    }
}
