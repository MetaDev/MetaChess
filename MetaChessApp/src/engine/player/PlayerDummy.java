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
public class PlayerDummy extends PlayerEmpty{

    public PlayerDummy(int side, ExtendedPieceModel controlledModel, String name, String core) {
        super(side, controlledModel, name, core);
        decreaseLivesOnKill = true;
    }
    
}
