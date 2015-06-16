/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.piece;

import engine.board.ExtendedBoardModel;

/**
 *
 * @author Harald
 */
public class ExtendedBischopPawn extends ExtendedPieceModel{
    
    public ExtendedBischopPawn(ExtendedBoardModel board,ExtendedBishopModel commander) {
        super(PieceType.pawn, board, 0.5f, 0);
        this.commander=commander;
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
