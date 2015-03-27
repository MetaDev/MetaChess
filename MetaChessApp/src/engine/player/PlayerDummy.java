/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.player;

import engine.piece.ExtendedPieceModel;

/**
 *
 * @author Harald
 */
//a dummy for a real player, pushable and killable
public class PlayerDummy extends Player{

    public PlayerDummy( ExtendedPieceModel controlledModel) {
        super((int)controlledModel.getColor(), controlledModel, "dummy", "pawn");
        decreaseLivesOnKill = true;
        name="dummy";
    }
    
}
