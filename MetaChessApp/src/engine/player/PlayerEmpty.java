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
//empty player, pushable but not killable
public class PlayerEmpty extends Player{

    public PlayerEmpty(int side, ExtendedPieceModel controlledModel, String name, String core) {
        super(side, controlledModel, name, core);
        pushable=true;
    }
    //empty player does nothing
}
