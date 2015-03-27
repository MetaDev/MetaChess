package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;

public class ExtendedQueenModel extends ExtendedPieceModel {
//Keep a set of decisionmodels with all allowed decisions for this piece

    

    public ExtendedQueenModel(ExtendedBoardModel board,int side) {
        super(PieceType.queen,board, side, 8);
        Directions.getDiagDirections(allowedMovement);
        Directions.getOrthoDirections(allowedMovement);
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        //do nothing
    }

}
