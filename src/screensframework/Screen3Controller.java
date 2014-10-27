/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License"). You
 * may not use this file except in compliance with the License. You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */ 
package screensframework;

import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
/**
 * FXML Controller class
 *
 * @author Angie
 */





class MyOutterGrid extends GridPane implements MouseListener{    
    
    private SlotImageView[][] slotsImgArray;
//    private MyInnerGrid[][] boardSlotsUI;  // Amount of boardSize*boardSize of these
    
    public Node getNodeByRowColumnIndex(final int row,final int column,MyOutterGrid gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for(Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
    
   
    
    public MyOutterGrid(int boardSize){
        slotsImgArray = new SlotImageView[boardSize][boardSize];
        
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                
                this.add(new MyInnerGrid(), i, j);
            }
        }
        MyInnerGrid inner = (MyInnerGrid)getNodeByRowColumnIndex(boardSize-1, 0, this);
        ImageView imgView = new ImageView();
        Image img = new Image("Pictures/board-slots/PlayerStatus/p1-4"+".png");
        imgView.setImage(img);
        inner.add(imgView,0,0);

        ImageView imgView1 = new ImageView();
        Image img1 = new Image("Pictures/board-slots/PlayerStatus/p2-4"+".png");
        imgView1.setImage(img1);
        inner.add(imgView1,3,0);
        
        ImageView imgView2 = new ImageView();
        Image img2 = new Image("Pictures/board-slots/PlayerStatus/p3-4"+".png");
        imgView2.setImage(img2);
        inner.add(imgView2, 0, 3);
        
        ImageView imgView3 = new ImageView();
        Image img3 = new Image("Pictures/board-slots/PlayerStatus/p4-4"+".png");
        imgView3.setImage(img3);
        inner.add(imgView3, 3, 3);
        inner.toFront();
        
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        int outr;
        outr = 6;
        outr++;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
      //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

class SlotImageView extends ImageView{
    private final int slotNum;
    
    public SlotImageView(int slotNum){
        this.slotNum = slotNum;
    }
    
    public int getSlotImageSlotNum(){
        return this.slotNum;
    }
}

class MyInnerGrid extends GridPane implements MouseListener{

    private ImageView[] soldiersRepresentationArray;
    private int m_SlotId;
    // size of each instance of this class: 3*3 grid
    public MyInnerGrid() {
        super();
        soldiersRepresentationArray = new ImageView[4];
    }
    
    // getter methods

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) 
    {
           int i;
           i = 6;
           i++;
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) 
    {
        //"Not supported yet."
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) 
    {
        //"Not supported yet."
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e)
    {
       //"Not supported yet."
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) 
    {
        //"Not supported yet."
    }
}




public class Screen3Controller extends StackPane implements Initializable, ControlledScreen {

    ScreensController myController;
    
    @FXML
    BorderPane gameBorderPane;
    
//    @FXML
    //private GridPane gameBoardGridPane;
    private MyOutterGrid gameBoardGrid; 
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO       
        
        gameBoardGrid = new MyOutterGrid(5);
        gameBorderPane.setLeft(gameBoardGrid);
        
        int k = 4;
        int fileNum = 1;
        for (int i = 1; i < 6; i++){
            for (int j = 0; j < 5; j++){
                
                SlotImageView iv1 = new SlotImageView(fileNum);
                Image img; 
                img = new Image("Pictures/board-slots/"+fileNum+".png");            
                
                fileNum++;
                iv1.setImage(img);
                
                gameBoardGrid.add(iv1, j, k); 
            }
            k--;
            
        }
        
        MyInnerGrid inner = (MyInnerGrid)gameBoardGrid.getNodeByRowColumnIndex(4, 0, gameBoardGrid);
        ImageView imgView = new ImageView();
        Image img = new Image("Pictures/board-slots/PlayerStatus/p1-4"+".png");
        imgView.setImage(img);
        inner.add(imgView, 0, 0);

        ImageView imgView1 = new ImageView();
        Image img1 = new Image("Pictures/board-slots/PlayerStatus/p2-4"+".png");
        imgView1.setImage(img1);
        inner.add(imgView1, 3, 0);
        
        ImageView imgView2 = new ImageView();
        Image img2 = new Image("Pictures/board-slots/PlayerStatus/p3-4"+".png");
        imgView2.setImage(img2);
        inner.add(imgView2, 0, 3);
        
        ImageView imgView3 = new ImageView();
        Image img3 = new Image("Pictures/board-slots/PlayerStatus/p4-4"+".png");
        imgView3.setImage(img3);
        inner.add(imgView3, 3, 3);
        inner.setVgap(11);
        inner.setHgap(5);
        inner.setAlignment(Pos.CENTER);
        inner.toFront();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        gameBoardGridPane = new GridPane();
//        gameBorderPane.setLeft(gameBoardGridPane);
//        
//        int k = 4;
//        int fileNum = 1;
//        for (int i = 1; i < 6; i++){
//            
//            for (int j = 0; j < 5; j++){
//                ImageView iv1;
//                Image img; 
//
//                img = new Image("Pictures/board-slots/"+fileNum+".png");            
//                fileNum++;
//                iv1 = new ImageView();                    
//                iv1.setImage(img);
//    //            iv1.
//    //            iv1.setOnMouseClicked(new EventHandler<MouseEvent> (){
//    //
//    //                @Override
//    //                public void handle(MouseEvent t) {
//    //                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    //                }
//    //            
//    //            });
//                ImageView _img; 
//                gameBoardGridPane.add(iv1, j, k);  
////                gameBoardGridPane.getChildren().add;
//            }
//            k--;
//            
//        }
       
    }    
    
    @Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);
    }
}



























