package engine.piece;

import engine.Directions;

public class ExtendedBishopModel extends ExtendedPieceModel {


    

    public ExtendedBishopModel(int side) {
        super(PieceType.BISHOP, side, 4);
         Directions.getDiagDirections(allowedMovement);
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            if (extendedSpecial) {
                penetrateLowerFraction = true;
            }
            increasedMovementRange = range;
        } else {
            penetrateLowerFraction = false;
            increasedMovementRange = 0;
        }

    }

    

}
