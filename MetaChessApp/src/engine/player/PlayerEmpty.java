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
public class PlayerEmpty extends Player{

    public PlayerEmpty(int side, ExtendedPieceModel controlledModel, String name, int[][] core) {
        super(side, controlledModel, name, core);
        pushable=true;
    }
    //empty player does nothing
}
