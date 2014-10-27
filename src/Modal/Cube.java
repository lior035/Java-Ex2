/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Modal;

/**
 *
 * @author Daniel Better
 * This class will be responsible for the game cube and the generation of a "Turn" steps, per player.
 */
public class Cube {
    
    public Cube(){
        // empty c'tor, we don't really need anything here
    }
    
    public int drawStepsWithCube(){
      int range = (6 - 1) + 1;     
      return (int)(Math.random() * range) + 1;
    }
    
    
}

