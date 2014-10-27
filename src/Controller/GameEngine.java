//package Controller;
//
//
//import Modal.GameConstant;
//import XMLUtils.XMLDocument;
//import View.UI;
//import View.ConsoleUI;
//import Modal.ComputerPlayer;
//import Modal.HumanPlayer;
//import Modal.Ladder;
//import Modal.Player;
//import Modal.Board;
//import Modal.BoardCoords;
//import Modal.BoardMovementElement;
//import Modal.Cube;
//import Modal.Snake;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GameEngine {
//    
//    private XMLDocument xml;
//    
//    private Board gameBoard;
//    private final UI gameInterface;
//    private ArrayList<Player> gamePlayers;
//    private final Cube gameCube;
//    private short numOfSoldiersNeededToWin;
//    private String gameName;
//    private static String lastUsedFileName;
//
//    public GameEngine() {
//        //initalize basic component that des not requires the ui yet
//        this.gameInterface = new ConsoleUI();
//        this.gameCube = new Cube();
//        this.gamePlayers = new ArrayList<Player>();
//        gameName = null;
//    }
//    
//    
//    void initializeGameComponents()
//    {
//        Board[] localBoard = new Board[1];
//        UI.gameModeOptions gameMode;
//        //initialize the game component from user input data. components include the game board and players details
//        while(true)
//        {
//            try
//            {   
//                gameMode = gameInterface.getGameModeOption();
//                gameInterface.initializeGameSettings(gameMode, localBoard);
//                break;
//            }
//            catch(Exception e)
//            {
//                gameInterface.alertFailedInitalize();
//            }
//        }
//         
//        this.numOfSoldiersNeededToWin = gameInterface.getNumOfSoldiersNeededToWin();
//        this.gameName = gameInterface.getGameName();
//        
//        switch (gameMode) {
//            case LOAD:
//                this.gameBoard = localBoard[0];
//                  this.gamePlayers = gameInterface.getPlayersList();  
//                  break;
//            case NEW:
//                 this.gameBoard = new Board(gameInterface.getBoardSize(), gameInterface.getNumOfPlayers(), gameInterface.getNumOfBoardMovementElements());
//                 initializePlayersDetails();
//                 placeMovementElementsOnBoard();
//                 break;
//        }      
//    }
//    
//    void placeMovementElementsOnBoard()
//    {
//        int range = (gameBoard.getBoardSize() - 2) + 1; 
//        
//        ArrayList<BoardCoords> takenCoords = new ArrayList<BoardCoords>();
//        
//        int currNumOfCreatedElementsFromSameType = 0;
//        int numOfElementsFromSameType = gameBoard.getNumberOfMovementElements();
//        int lastCoords = gameBoard.getBoardSize();
//        
//       BoardCoords demoCoordsToPreventSnakeBeingAtTheEndOfBoard = new BoardCoords(lastCoords, lastCoords-1);
//       takenCoords.add(demoCoordsToPreventSnakeBeingAtTheEndOfBoard);
//       
//       BoardCoords demoCoordsToPreventLadderBeingAtTheStartOfBoard = new BoardCoords(1, 0);
//
//       
//     
//       
//       boolean isFirstDemoRemoved = false;
//            
//        while(currNumOfCreatedElementsFromSameType != (numOfElementsFromSameType*GameConstant.NumberOfTypeOfBoardMovementElements))
//        {
//            //draw coord
//            BoardCoords startCoord = drawCoord(range,takenCoords);
//            takenCoords.add(startCoord);
//            
//            BoardCoords endCoord = drawCoord(range, takenCoords);
//            takenCoords.add(endCoord);
//            
//            
//            //try to create element of first type
//            
//            if(currNumOfCreatedElementsFromSameType < numOfElementsFromSameType)
//            {
//                try
//                {
//                    BoardMovementElement bme = new Snake(startCoord, endCoord, gameBoard.getBoardSize());
//                    gameBoard.PlaceMovementElementsOnBoard(bme);
//                    currNumOfCreatedElementsFromSameType++;
//                }
//                catch(Exception e)
//                {
//                    //before re-entering the loop, clean the coords that were draw in this iteration
//                    takenCoords.remove(startCoord);
//                    takenCoords.remove(endCoord);
//                }
//            }
//            else
//            {
//                if(isFirstDemoRemoved == false)
//                {
//                    takenCoords.remove(demoCoordsToPreventSnakeBeingAtTheEndOfBoard);
//                    takenCoords.add(demoCoordsToPreventLadderBeingAtTheStartOfBoard);
//                    isFirstDemoRemoved = true;
//                }
//                
//                 try
//                {
//                    BoardMovementElement bme = new Ladder(startCoord, endCoord, gameBoard.getBoardSize());
//                    gameBoard.PlaceMovementElementsOnBoard(bme);
//                    currNumOfCreatedElementsFromSameType++;
//                }
//                catch(Exception e)
//                {
//                    //before re entering the loop, clean the coords that were draw in this iteration
//                    takenCoords.remove(startCoord);
//                    takenCoords.remove(endCoord);
//                }
//            }
//        }
//        
//              takenCoords.remove(demoCoordsToPreventLadderBeingAtTheStartOfBoard);
//        
//    }
//
//    private BoardCoords drawCoord(int range, ArrayList<BoardCoords> takenCoords) {
//        
//        BoardCoords drawBoardCoord;
//        
//        int xCoord;
//        int yCoord;
//        
//        do
//        {
//            xCoord = (int)(Math.random() * range) + 1;  
//            yCoord = (int)(Math.random() * range) + 1; 
//            drawBoardCoord = new BoardCoords (xCoord, yCoord);
//        }while (nonTakenCoords(drawBoardCoord, takenCoords) == false);
//        
//        return drawBoardCoord;
//    }
//
//    private boolean nonTakenCoords(BoardCoords drawBoardCoord, ArrayList<BoardCoords> takenCoords) {
//        
//        for(BoardCoords currentIterationCoord : takenCoords)
//        {
//            if ( (drawBoardCoord.getBoardXCoord() == currentIterationCoord.getBoardXCoord() )&& (drawBoardCoord.getBoardYCoord() == currentIterationCoord.getBoardYCoord()) )
//            {
//                return false;
//            }
//        }
//        
//        return true;
//    }
//    
//    public Board getGameBoard(){
//        return this.gameBoard;
//    }
//
//    private void initializePlayersDetails() 
//    {
//        boolean isCurrentPlayerHuman;
//        String playerName;
//        gamePlayers.clear();
//        for(int i=0; i<gameInterface.getNumOfPlayers(); i++)
//        {
//            gameInterface.printAskDetailsOfPlayerMsg(i);
//            isCurrentPlayerHuman = gameInterface.reciveFromUserThePlayerTypeInfo();
//            playerName = gameInterface.reciveFromUserThePlayerName(gamePlayers);
//            
//            if(isCurrentPlayerHuman)
//            {
//                Player newPlayerToAdd = new HumanPlayer(playerName, i);
//                gamePlayers.add(newPlayerToAdd);
//            }
//            else
//            {
//                Player newPlayerToAdd = new ComputerPlayer(playerName, i);
//                gamePlayers.add(newPlayerToAdd);
//            }
//        }
//    }
//    
//    public void run(){
//        
//        boolean stopPlaying;
//        this.gameInterface.printWelcomeAndGameDisclaimer();
//        do{
//            initializeGameComponents();
//            stopPlaying = runSingularGame();
//            
//            if (stopPlaying == false){
//                stopPlaying = gameInterface.recieveFromUserNewGameAnswer();
//            }
//           
//        } while (stopPlaying == false);
//    }
//    
//    private boolean runSingularGame(){
//        
//        // Simulate each player's turn
//        boolean endGame = false;
//        short cubeValue;
//        boolean hasPlayerWon = false;
//       
//        while (hasPlayerWon == false){
//            for(Player currPlayer : gamePlayers){
//                
//                //in case someone quit and we left alone - end game
//                if(isPlayerAlone(gamePlayers)==true)
//                {
//                    hasPlayerWon = true;
//                    break;
//                }
//                
//                if(currPlayer.getPlayingStatus()==false)
//                {
//                    continue;
//                }
//                
//                 // Start by printing a current board with everything included to the user
//                gameInterface.printGameStatus(gameBoard, gamePlayers);
//                
//                // Print out messages to alert the user that it's "his" move and recieve his input
//                gameInterface.printPlayerTurn(currPlayer);
//                cubeValue = (short)this.gameCube.drawStepsWithCube();
//                gameInterface.printCubeInformation(cubeValue);
//               
//                //============start take care according to type==================
//                
//                if(currPlayer instanceof HumanPlayer)
//                {
//                     UI.TurnDecision td = null;
//                     
//                     short [] soldier = new short[1]; 
//                     
//                     boolean continueLoop = true;
//
//                     while(continueLoop)
//                     {
//                           //here - assuming it is console ui
//                            if(gameInterface instanceof ConsoleUI)
//                            {
//
//                                td = ((ConsoleUI)gameInterface).recieveTurnDecisionFromPlayer(soldier, currPlayer.getAllSoldiers());
//                            }
//                            
//                            switch (td)
//                            {
//                                case SAVE:
//                                    this.saveGame(currPlayer.getPlayerName());
//                                    continueLoop = true;
//                                    endGame = false;
//                                    break;
//                                case QUIT:
//                                    this.removePlayer(currPlayer);
//                                    continueLoop =  false;
//                                    endGame = false;
//                                    break;
//                                case SHUTDOWN:
//                                    continueLoop = false;
//                                    endGame = true;
//                                    break;
//                                case PLAY:
//                                    continueLoop = false;
//                                    endGame = false;
//                                    break;
//                                default:
//                                    break;
//                             }
//                        }
//                     
//                     currPlayer.move(cubeValue, gameBoard, soldier[0]);
//                     if(endGame == true)
//                     {
//                         return true;
//                     }
//                     
//                }
//                
//                else if(currPlayer instanceof ComputerPlayer)
//                {
//                    //0 is sent as garbage param since computer not use it
//                    currPlayer.move(cubeValue, gameBoard, (short)0);
//                }
//                
//                //============end take care according to type====================
//                
//                //call move method of player - here and check the return value
//               
//                if(currPlayer.getPlayingStatus() == false)
//                {
//                    //since human player can quit, and therefor go to next player
//                    continue;
//                }
//                
//                if (currPlayer.hasPlayerWon(numOfSoldiersNeededToWin) == true){
//                    hasPlayerWon = true;
//                    
//                    gameInterface.printGameFinish();
//                    
//                    gameInterface.printGameStatus(gameBoard, gamePlayers);
//                    break;
//                }
//            }
//            
//            
//        }
//        
//        return false;
//    }
// 
//    UI getInterface(){
//        return this.gameInterface;
//    }
//    
//      void saveGame(String playerName) {
//          
//          if(this.gameInterface instanceof ConsoleUI)
//          {
//              xml = new XMLDocument();
//              do{
//                  this.lastUsedFileName = ((ConsoleUI)this.gameInterface).receiveFromUserFilePathToSave(this.lastUsedFileName);
//                  try
//                  {
//                    xml.createDocument(this.lastUsedFileName, this.gameBoard, this.gamePlayers,playerName, this.gameName, this.numOfSoldiersNeededToWin);
//                    break;
//                  }
//                  
//                  catch(Exception e)
//                  {
//                      ((ConsoleUI)this.gameInterface).printErrorMsgWhileSaving();
//                  }
//               }while(true);
//          }
//          
//          this.gameInterface.printSaveSuccess();
//      }
//
//
//      void removePlayer(Player player) {
//        player.setPlayingStatusToFalse();
//    }
//
//    
//      public void setName(String gameName)
//      {
//          this.gameName = gameName;
//      }
//      
//    private boolean isPlayerAlone(ArrayList<Player> gamePlayers) {
//        int count = 0;
//        for(Player p: gamePlayers)
//        {
//            if(p.getPlayingStatus()==true)
//            {
//                count++;
//            }
//        }
//        
//        if (count==1)
//        {
//            return true;
//        }
//        
//        return false;
//    }
//}
