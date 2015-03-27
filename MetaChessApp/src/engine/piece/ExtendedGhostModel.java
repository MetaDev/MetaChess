/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;
import meta.MetaConfig;

/**
 *
 * @author Harald
 */
/**
* A basic piece able to move vert and hor
*It can't be killed, increasing move range also costs cooldown
* Not sure how to use yet, to spy while the games is busy.
* to explore the board after being killed, maybe a special
* maybe a special class
*
*/
public class ExtendedGhostModel extends ExtendedPieceModel{

    public ExtendedGhostModel(PieceType type,ExtendedBoardModel board, int side, int lives) {
        super(type,board, side, lives);
    }
    //TODO
    //override behoaviour of 

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
