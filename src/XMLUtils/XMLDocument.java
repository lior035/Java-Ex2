/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package XMLUtils;

import Modal.Board;
import Modal.BoardSlot.Type;
import static Modal.BoardSlot.Type.LADDER;
import Modal.BoardSlot;
import Modal.ComputerPlayer;
import Modal.HumanPlayer;
import Modal.Player;
import Modal.Soldier;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author danie_000
 */
public class XMLDocument {
    
        static private final String xsdFilePath = "snakesandladders.xsd";
        
        public XMLDocument(){
            // empty c'tor
        }
        
        
        /* Various sources I used for this part:
            1. http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
            2. http://stackoverflow.com/questions/1384802/java-how-to-indent-xml-generated-by-transformer
            3. http://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Document.html
        */
        public void createDocument(String filename, Board gameBoard, List<Player> playerList, String currPlayersName, String gameName, short numOfSoldierNeededToWin ) throws ParserConfigurationException, TransformerException{
             String gameSavedDir;
            try {

                
                  DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                  DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

                  
               // --------------------------   root element --------------------
                          Document doc = docBuilder.newDocument();
                  Element rootElement = doc.createElement("snakesandladders");
                  Attr _attr_xsi = doc.createAttribute("xsi:noNamespaceSchemaLocation");
                  _attr_xsi.setValue("snakesandladders.xsd");
                  Attr _attr_xmlns = doc.createAttribute("xmlns:xsi");
                  _attr_xmlns.setValue("http://www.w3.org/2001/XMLSchema-instance");
                  Attr _attrCurrPlayer = doc.createAttribute("current_player");
                  _attrCurrPlayer.setValue(currPlayersName);
                  Attr _gameName = doc.createAttribute("name");
                  _gameName.setValue(gameName);
                  Attr _numOfSoldiersToWin = doc.createAttribute("number_of_soldiers");
                  _numOfSoldiersToWin.setValue(Short.toString(numOfSoldierNeededToWin));
                  
                  
                  rootElement.setAttributeNode(_attr_xmlns);
                  rootElement.setAttributeNode(_attr_xsi);
                  rootElement.setAttributeNode(_attrCurrPlayer);
                  rootElement.setAttributeNode(_gameName);
                  rootElement.setAttributeNode(_numOfSoldiersToWin);
                  doc.appendChild(rootElement);
                  

              // -----------------------   define elements ---------------------
                  Element players = doc.createElement("players");
                  rootElement.appendChild(players);

                  Element board = doc.createElement("board");
                  rootElement.appendChild(board);
              
               // ----------------------   end define elements -----------------
                 
                  Element currPlayerElem;
                  Attr typeAttribute;
                  Attr nameAttribute;
                  Player currPlayer;
                  
                  for (int i = 0; i < playerList.size(); i++){
                      
                      currPlayerElem = doc.createElement("player");
                      
                      currPlayer = playerList.get(i);
                      String playerName = currPlayer.getPlayerName();
                      
                      typeAttribute = doc.createAttribute("type");
                      if (currPlayer instanceof HumanPlayer){
                          typeAttribute.setValue("HUMAN");
                      } else if (currPlayer instanceof ComputerPlayer) {
                          typeAttribute.setValue("COMPUTER");
                      }
                      
                      nameAttribute = doc.createAttribute("name");
                      nameAttribute.setValue(playerName);
                      
                      currPlayerElem.setAttributeNode(typeAttribute);
                      currPlayerElem.setAttributeNode(nameAttribute);
                      players.appendChild(currPlayerElem);
                  }
                  
                  Attr _boardSizeAttr = doc.createAttribute("size");
                  short gameBoardSize = (short)gameBoard.getBoardSize();
                  _boardSizeAttr.setValue(Short.toString(gameBoardSize));
                  board.setAttributeNode(_boardSizeAttr);
                  
                  Element cells = doc.createElement("cells");
                  board.appendChild(cells);
                  
                  
                  Element cell;
                  Element soldiers;
                  Element ladders = doc.createElement("ladders");
                  Element snakes = doc.createElement("snakes");
                  
                  Type slotType;
                  short boardSize = gameBoard.getBoardSize();
                  BoardSlot currSlot;
                  ArrayList<Integer> soldiersAmtPerPlayer;                  
                  
                  for (int i = 1; i <= boardSize*boardSize; i++){
                      
                      currSlot = gameBoard.getBoardSlot((short)i);
                      
                      if (currSlot.isSlotOccupied() == true){
                          
                            cell = doc.createElement("cell");
                            Attr _cellNumber = doc.createAttribute("number");
                            _cellNumber.setValue( Short.toString(currSlot.getSlotNumber()));
                            cell.setAttributeNode(_cellNumber);
                            
                            soldiersAmtPerPlayer = currSlot.getSoldiersAmtPerPlayerArrayList();
                            
                            for (int j = 0; j < soldiersAmtPerPlayer.size(); j++){
                                // Loop through the number of players
                                
                                
                                if ((soldiersAmtPerPlayer.get(j) != 0) && (playerList.get(j).getPlayingStatus() == true)){
                                    // It means the player with index i has atleast one soldier on this slot
                                    
                                    soldiers = doc.createElement("soldiers");
                                    
                                    Attr playerName = doc.createAttribute("playerName");
                                    playerName.setValue(playerList.get(j).getPlayerName());
                                    
                                    Attr count = doc.createAttribute("count");
                                    count.setValue(Integer.toString(soldiersAmtPerPlayer.get(j)));
                                    
                                    // Add both attirubtes to "soldier" element
                                    soldiers.setAttributeNode(playerName);
                                    soldiers.setAttributeNode(count);
                                    
                                    cell.appendChild(soldiers);
                                    cells.appendChild(cell);
                                }
                            }
                      }
                      
                      // Check for board movement elements
                      slotType = currSlot.getSlotType();
                      if (slotType != Type.REG){
                          
                          Element _boardMovementElement;
                          Attr _boardMovementAttributeFrom; 
                          Attr _boardMovementAttributeTo; 
                          switch (slotType){
                              case LADDER:
                                  _boardMovementElement = doc.createElement("ladder");
                                  _boardMovementAttributeFrom = doc.createAttribute("from");
                                  _boardMovementAttributeFrom.setValue(Short.toString(currSlot.getSlotNumber()));
                                  _boardMovementAttributeTo = doc.createAttribute("to");
                                  _boardMovementAttributeTo.setValue(Short.toString((short)currSlot.getMovesTo()));
                                  
                                  _boardMovementElement.setAttributeNode(_boardMovementAttributeFrom);
                                  _boardMovementElement.setAttributeNode(_boardMovementAttributeTo);
                                  
                                  ladders.appendChild(_boardMovementElement);
                                  board.appendChild(ladders);
                                  break;
                                  
                              case SNAKE:
                                  _boardMovementElement = doc.createElement("snake");
                                  _boardMovementAttributeFrom = doc.createAttribute("from");
                                  _boardMovementAttributeFrom.setValue(Short.toString(currSlot.getSlotNumber()));
                                  _boardMovementAttributeTo = doc.createAttribute("to");
                                  _boardMovementAttributeTo.setValue(Short.toString((short)currSlot.getMovesTo()));
                                  
                                  _boardMovementElement.setAttributeNode(_boardMovementAttributeFrom);
                                  _boardMovementElement.setAttributeNode(_boardMovementAttributeTo);
                                  
                                  snakes.appendChild(_boardMovementElement);
                                  board.appendChild(snakes);
                                  break;
                          }
                                  
                      }
                  }
                  

                  TransformerFactory transformerFactory = TransformerFactory.newInstance();
                  Transformer transformer = transformerFactory.newTransformer();
                  transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                  transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
                  DOMSource source = new DOMSource(doc);
                  gameSavedDir = "GameSaves/"+filename+".xml";
                  StreamResult result = new StreamResult(new File(gameSavedDir));

                  transformer.transform(source, result);


            } catch ( ParserConfigurationException | TransformerException e) {
                  throw e;
            }
       }
        
       public Board readXMLFile(String fileName, List<Player> playerList, short[] numSoldiersNeededToWin, ArrayList<String> gameName) throws Exception{

           String savedGameNameDir = "GameSaves/"+fileName+".xml";
           Board gb;
           Player currPlayer = null; 
           
           List<BoardSlot> bmeList = new ArrayList<BoardSlot>();
           List<BoardSlot> activeSlots = new ArrayList<BoardSlot>();
           
           short boardSize;
           short numOfPlayers = 0;
           BoardSlot currSlot; 
           NodeList nList;
           Node nNode;
           Element eElement;
           
           int currPlayersSoldiersAmtOnSlot; 
           String currPlayerName;
           Player tmpPlayer;
           NodeList _nList;
           Element _elem;
           Node _nNode;
           
           short slotNum;
           int movesTo; 
           short numSoldiersToWin;
           
           String currPlayerTurnsName; 
           Board tmpBoard = null;
           String _gameName;
           
           try { 
               try{
                   validateXMLDocument(savedGameNameDir, XMLDocument.xsdFilePath);
               } catch (SAXException | IOException e) {
                   throw e;
               }
            
                
                File xmlFile = new File(savedGameNameDir);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);

                
                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                               
                
                nList = doc.getElementsByTagName("snakesandladders");
                nNode = nList.item(0); 
                eElement = (Element) nNode; 
                currPlayerTurnsName = eElement.getAttribute("current_player");
                _gameName = eElement.getAttribute("name");
                
                gameName.add(_gameName);
                numSoldiersToWin = Short.parseShort(eElement.getAttribute("number_of_soldiers"));
                numSoldiersNeededToWin[0] = numSoldiersToWin;
                
                 
                
                // Handle board size read 
                // ------------------------------------------------------------------------------
                nList = doc.getElementsByTagName("board");
                nNode = nList.item(0);
                eElement = (Element) nNode; 
                boardSize = Short.parseShort(eElement.getAttribute("size"));
                
                
                
                // Handle Different Players read
                // ------------------------------------------------------------------------------
                nList = doc.getElementsByTagName("player");
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        numOfPlayers++;
                        nNode = nList.item(temp);

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                eElement = (Element) nNode;
                                
                                String playerType = eElement.getAttribute("type");
                                String playerName = eElement.getAttribute("name");
                                
                                switch (playerType){
                                        case "HUMAN":
                                            currPlayer = new HumanPlayer(playerName, temp, numSoldiersToWin);
                                            break;
                                        case "COMPUTER":
                                            currPlayer = new ComputerPlayer(playerName, temp, numSoldiersToWin);
                                            break;
                                }
                                playerList.add(currPlayer);
                        }
                }
                
                
                // Sanity check 
                // -----------------------------------------------------------
                // make sure there is no such case where a player that is not defined in the <players> name suddenly appers on the 
                // <soldiers> tab
                
                nList = doc.getElementsByTagName("soldiers");
                String currSoldiersPlayerName;
                ArrayList<String> soldierPlayerNames = new ArrayList<String>();
                
                for (int i = 0; i < nList.getLength(); i++){
                    nNode = nList.item(i);
                    
                    if (nNode.getNodeType() == Node.ELEMENT_NODE){
                        eElement = (Element) nNode;
                        currSoldiersPlayerName = eElement.getAttribute("playerName");
                        if (i == 0){
                            // the first name we meet, insert it
                            soldierPlayerNames.add(currSoldiersPlayerName);
                        }
                        for (int j = 0; j < soldierPlayerNames.size(); j++){
                            if (soldierPlayerNames.get(j).equalsIgnoreCase(currSoldiersPlayerName)){
                                // continue
                            } else{
                                // found a new name
                                soldierPlayerNames.add(currSoldiersPlayerName);
                            }
                        }
                    }
                }
                
                boolean flag = true; 
                for (int i = 0; i < playerList.size(); i++){
                    
                    for (int j = 0; j < soldierPlayerNames.size(); j++){
                        if (playerList.get(i).getPlayerName().equalsIgnoreCase(soldierPlayerNames.get(j))){
                            flag = true; 
                            break;
                        } else{
                            flag = false;
                        }
                    }
                    
                    if (flag == false) break;
                }
                
                if (flag == false){
                    XMLExceptions.throwPlayerNamesDontMeetSoldierOwnerNames();
                }
                
                // -- Sanity check passed, continue
                
                
                
                
                // Sanity check
                // ------------------------------------------------------------ 
                // check players names attribute, no two names should be identical
                // make sure there are no two players with the same name
                
                for (int i = 0; i < playerList.size(); i++){
                    for (int j = 0; j < playerList.size(); j++){
                        if ((playerList.get(i).getPlayerName().equalsIgnoreCase(playerList.get(j).getPlayerName())) && (i != j)){
                            XMLExceptions.throwNamesError();
                        }
                    }
                }
                
                // -- Sanity check passed, continue
                
                // Re-arrange the order so that the first element is the player whose turn is next:
                Player _player = null; 
                int _playerIndex = 0;
                
                for (int i = 0; i < playerList.size(); i++){
                    if ( (playerList.get(i).getPlayerName().equalsIgnoreCase(currPlayerTurnsName)) ){
                        _player = playerList.get(i);
                        _playerIndex = i;
                        break;
                    }
                }
                
                playerList.remove(_playerIndex);
                playerList.add(0, _player);
                
                for (int i = 0; i < playerList.size(); i++){
                    playerList.get(i).setId(i);
                }
                
                // Handle Board Movement Elements read
                // ------------------------------------------------------------------------------
                short numberOfBME = 0;
                short numOfLadderBME = 0;
                short numOfSnakesBME = 0;
                nList = doc.getElementsByTagName("ladder");
                
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        nNode = nList.item(temp);
                       
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            
                                numberOfBME++;
                                numOfLadderBME++;
                                eElement = (Element) nNode;
                                
                                slotNum = Short.parseShort(eElement.getAttribute("from"));
                                movesTo = Integer.parseInt(eElement.getAttribute("to"));
                                
                                currSlot = new BoardSlot(numOfPlayers, slotNum);
                                currSlot.setMovesTo(movesTo);
                                currSlot.setSlotType(Type.LADDER);
        
                                bmeList.add(currSlot);
                        }
                }
                
                
                nList = doc.getElementsByTagName("snake");
                
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        nNode = nList.item(temp);
                        
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                numOfSnakesBME++;
                                eElement = (Element) nNode;
                                
                                slotNum = Short.parseShort(eElement.getAttribute("from"));
                                movesTo = Integer.parseInt(eElement.getAttribute("to"));
                                
                                currSlot = new BoardSlot(numOfPlayers, slotNum);
                                currSlot.setMovesTo(movesTo);
                                currSlot.setSlotType(Type.SNAKE);
        
                                bmeList.add(currSlot);
                        }
                }
                
                
                // Sanity Check 
                // -------------------------------------------------------------
                // check bme attributes:
                // 1. No snake is located at the last spot
                // 2. No ladder is on the first spot
                
                for (int i = 0; i < bmeList.size(); i++){
                    
                    switch (bmeList.get(i).getSlotType()){
                        case SNAKE: 
                            if (bmeList.get(i).getSlotNumber() == boardSize*boardSize){
                                // That means that the last slot number is of type SNAKE - forbiden 
                                XMLExceptions.throwSnakeError();
                            }
                            break;
                        case LADDER:
                            if (bmeList.get(i).getSlotNumber() == 1){
                                XMLExceptions.throwLadderError();
                            }
                        
                    }
                }
                
                // -- Check succeeded, continue
                
                
                // Sanity Check 
                // -------------------------------------------------------------
                // Is there a snake and a ladder on the same slot?
                
                for (int i = 0; i < numOfLadderBME; i++){
                    for (int j = numOfLadderBME; j < bmeList.size(); j++ ){
                        
                        if ( bmeList.get(i).getSlotNumber() == bmeList.get(j).getSlotNumber()){
                            // it means that both ladder and snake are "on" the same slot - not allowed
                            XMLExceptions.throwSnakeAndLadderOnSameSlot();
                        }
                    }
                }
                
                // -- check passed, continue
                
                // Sanity Check 
                // -------------------------------------------------------------
                // does the number of ladder equal the number of snakes? 
                if (numOfSnakesBME != numOfLadderBME){
                    XMLExceptions.throwNumOfBmeDoesntMatch();
                }
                
                // -- Check succeeded, continue
                
                tmpBoard = new Board(boardSize, (short)playerList.size(), numberOfBME);
                
                // Handle slots/cell read
                // ------------------------------------------------------------------------------
                nList = doc.getElementsByTagName("cell");
                
                for (int temp = 0; temp < nList.getLength(); temp++) {

                        nNode = nList.item(temp);
                        
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                eElement = (Element) nNode;
                                
                                slotNum = Short.parseShort(eElement.getAttribute("number"));
                                _nList = eElement.getElementsByTagName("soldiers");
                                
                                for (int j = 0; j < _nList.getLength(); j++){
                                    _nNode = _nList.item(j);
                                    if (_nNode.getNodeType() == Node.ELEMENT_NODE){
                                        _elem = (Element) _nNode;
                                        currPlayerName = _elem.getAttribute("playerName");
                                        currPlayersSoldiersAmtOnSlot = Integer.parseInt(_elem.getAttribute("count"));
                                        
                                        for (int i = 0; i < playerList.size(); i++){
                                            tmpPlayer = playerList.get(i);
                                            if (currPlayerName.equalsIgnoreCase(tmpPlayer.getPlayerName())){ // we found the correct player
                                                
                                                currSlot = tmpBoard.getBoardSlot(slotNum);
                                                // currsSlot now references the original board slot, so if we make changes to it
                                                // it's as if we changed the original board slot which is exactly what we want
                                                currSlot.changeAmountOfSoldierForPlayer(tmpPlayer.getId(), currPlayersSoldiersAmtOnSlot);
                                                
                                            }
                                        }
                                    }
                                }
                        }
                }
                
                for (int i = 0; i < bmeList.size(); i++){    
                    currSlot = bmeList.get(i);
                    tmpBoard.getBoardSlot(currSlot.getSlotNumber()).setSlotType(currSlot.getSlotType());
                    tmpBoard.getBoardSlot(currSlot.getSlotNumber()).setMovesTo(currSlot.getMovesTo());
                }
                
                
                // -- This section is for figuring out how many soldiers does each player have according to the XML --------
                // -- The correct number will be assigned to the current player as to reflect on his absoloute updated status
                int[] numOfSoldiersPerPlayer = new int[playerList.size()];
                for (int i = 0; i < playerList.size(); i++){
                    numOfSoldiersPerPlayer[i] = 0;
                }
                
                ArrayList<Integer> soldiersAmtPerSlot = null; 
                BoardSlot _bs;
                for (int i = 1; i <= boardSize*boardSize; i++){
                    _bs = tmpBoard.getBoardSlot((short)i);
                    
                    soldiersAmtPerSlot = _bs.getSoldiersAmtPerPlayerArrayList();
                    
                    for (int j = 0; j < playerList.size(); j++){
                        numOfSoldiersPerPlayer[j] = numOfSoldiersPerPlayer[j] + soldiersAmtPerSlot.get(j);
                    }
                }
                
                for (int i = 0; i < playerList.size(); i++){
                    
                    String name = playerList.get(i).getPlayerName();
                    int id = playerList.get(i).getId();
                    Player _tmpPlayer = null;
                    
                    if ( playerList.get(i) instanceof HumanPlayer){
                        short numOfSoldiers = (short)numOfSoldiersPerPlayer[i];
                        _tmpPlayer = new HumanPlayer(name, id, numOfSoldiers);
                        
                    } else if (playerList.get(i) instanceof ComputerPlayer){
                        short numOfSoldiers = (short)numOfSoldiersPerPlayer[i];
                        _tmpPlayer = new ComputerPlayer(name, id, numOfSoldiers); 
                    }
                    
                    playerList.remove(i);
                    playerList.add(i, _tmpPlayer);
                }
                
                // -- end previous section 
                
                BoardSlot _currSlot;
                ArrayList<Integer> soldiersPerSlotArray;
                int soldiersAmtPerCurrPlayerOnSlot;
              
                // loop through each one of the board slots 
                for (int i = 1; i <= boardSize*boardSize; i++){
                    soldiersAmtPerCurrPlayerOnSlot = 0;
                    
                    // for each board slot iterate over the number of players
                    for (int j = 0; j < playerList.size(); j++){
                        
                        _currSlot = tmpBoard.getBoardSlot((short) i);
                        soldiersPerSlotArray = _currSlot.getSoldiersAmtPerPlayerArrayList();
                        soldiersAmtPerCurrPlayerOnSlot = soldiersPerSlotArray.get(j);                
                        
                        // for the number of SOLDIERS found on the current (i) slot, per the current (j) player, iterate over all of them and set 
                        // their board slot to the current slot (i) 
                        for (int k = 0; k < soldiersAmtPerCurrPlayerOnSlot; k++){
                            Soldier _soldier = playerList.get(j).getSoldier((short) k);
                            _soldier.setBoardSlot((short) i);
                        }
                    }
                }
                
                // Before finishing the load, make sure we make every soldier that has already finished to "game status" inactive
                Player _tmpPlayer;
                Soldier[] _soldierArr;
                Soldier _tmpSoldier;
                int _currBoardSlot;
                for (int i = 0; i < playerList.size(); i++){
                    _tmpPlayer = playerList.get(i);
                    _soldierArr = _tmpPlayer.getAllSoldiers();
                    for (int j = 0; j < _soldierArr.length; j++){
                        _tmpSoldier = _soldierArr[j];
                        _currBoardSlot = _tmpSoldier.getBoardSlot();
                        if (_currBoardSlot == 25){
                            _tmpSoldier.setSoldierActiveStatusToFalse();
                        }
                    }
                }
                
                
                // Sanity check - make sure that each one of the players has the exact number of soldiers. 
                // ----------------------------------------------------------------------------------------
                int[] _numOfSoldiersPerPlayer = new int[playerList.size()];
                
                // initalize the newly created array
                for (int i = 0; i < _numOfSoldiersPerPlayer.length; i++){
                    _numOfSoldiersPerPlayer[i] = 0;
                }
                
                nList = doc.getElementsByTagName("soldiers");
                
                for (int i = 0; i < nList.getLength(); i++){
                    nNode = nList.item(i);
                    
                    if (nNode.getNodeType() == Node.ELEMENT_NODE){
                        eElement = (Element) nNode;
                        currSoldiersPlayerName = eElement.getAttribute("playerName");
                        int _currCount = Integer.parseInt(eElement.getAttribute("count"));
                        
                        for (int j = 0; j < playerList.size(); j++){
                            if (playerList.get(j).getPlayerName().equalsIgnoreCase(currSoldiersPlayerName)){
                                _numOfSoldiersPerPlayer[playerList.get(j).getId()] += _currCount;
                            }             
                        }
                    }
                }
                
                for (int i = 0; i < _numOfSoldiersPerPlayer.length-1; i++){
                    if (_numOfSoldiersPerPlayer[i] != _numOfSoldiersPerPlayer[i+1]){
                        // It means we have found two different soldiers who don't share the same amount of soldiers
                        // order an appropriate exception here
                        XMLExceptions.throwNonMatchingSoldiersAmtPerPlayer();
                    }
                }

            } catch (Exception e) {
                throw e;
            }
           
           return tmpBoard;
      }
      
         
         
      public void validateXMLDocument(String XMLpath, String XSDpath) throws SAXException, IOException{
          
                Source xmlValidationFile = new StreamSource(new File(XMLpath));
                Source xsdSchemaFile = new StreamSource(new File(XSDpath));
          
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = schemaFactory.newSchema(xsdSchemaFile);
                Validator validator = schema.newValidator();
                try {
                     validator.validate(xmlValidationFile);
                } catch (    SAXException | IOException e) {
                    throw e; 
                }
      }
}
