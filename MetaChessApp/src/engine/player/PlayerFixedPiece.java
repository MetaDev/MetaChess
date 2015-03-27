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
//this player can't swtich piece
public class PlayerFixedPiece extends Player{

    public PlayerFixedPiece(int side, ExtendedPieceModel controlledModel, String name, String core) {
        super(side, controlledModel, name, core);
        switchable=false;
    }
    
}
