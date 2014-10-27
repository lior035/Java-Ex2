package Modal;

/**
 *
 * @author danie_000
 */
public class Ladder extends BoardMovementElement{
    
    public Ladder(BoardCoords start, BoardCoords end, int boardSize) throws Exception{
        super(start, end, boardSize);
        if (( start.getBoardYCoord() > end.getBoardYCoord() ) ||
            ((  start.getBoardYCoord() == end.getBoardYCoord()) && (start.getBoardXCoord() > end.getBoardXCoord()))) {
            this.start = end;
            this.end = start;
        }
        else
        {
            this.start = start;
            this.end = end;
        }
    }
    
}
