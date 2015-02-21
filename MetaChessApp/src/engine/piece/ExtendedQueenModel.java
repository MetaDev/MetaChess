package engine.piece;

import engine.Directions;

public class ExtendedQueenModel extends ExtendedPieceModel {
//Keep a set of decisionmodels with all allowed decisions for this piece

    

    public ExtendedQueenModel(int side) {
        super(PieceType.queen, side, 8);
        Directions.getDiagDirections(allowedMovement);
        Directions.getOrthoDirections(allowedMovement);
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        //do nothing
    }

}
