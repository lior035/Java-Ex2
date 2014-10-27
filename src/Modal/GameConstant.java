package Modal;

public class GameConstant {

    private GameConstant() {
    }
    
    /*
      Note - no access modifier means that the field will be accessable only
      for the class and those who on the same package (i.e. world and subclass wont 
      be premeted). source - https://www.google.co.il/url?sa=i&rct=j&q=
      &esrc=s&source=images&cd=&cad=rja&uact=8&docid=ssAUNSPrP2m7dM&tbnid=
      CwsdmZJBpS0g_M:&ved=0CAUQjRw&url=http%3A%2F%2Fwww.mxtutorial.com
      %2F2011_08_01_archive.html&ei=f9jBU7HEDMTEPZzVgKgO&bvm=bv.70810081
      ,d.bGE&psig=AFQjCNHQKBQ4WMQaMQxEzXIM5nIler_Puw&ust=1405299195678807
    */
    
    public final static short MaxPlayers = 4;
    public final static short MinPlayers = 2;
     
    public final static short MaxNumOfElements = 6;
    public final static short MinNumOfElements = 4;
     
    public final static short MaxNumOfSoldiersNeededToWin = 4;
    public final static short MinNumOfSoldiersNeededToWin = 1;
     
    public final static short MaxBoardSize = 8;
    public final static short MinBoardSize = 5;
    
    public final static short NumberOfTypeOfBoardMovementElements = 2;
    public final static short NumberOfSoldiersPerPlayer = 4;
    
    public final static short minimumGameMode = 1;
    public final static short maximalGameMode = 2;
    
    public final static short minSoldierID = 0;
    public final static short maxSoldierID = 3;
    
    public final static short maxOptionInSaveMenu = 2;
    public final static short minOptionInSaveMenu = 1;
    
    public final static short humanPlayerType = 1;
    public final static short computerPlayerType = 2;
}
