package engine.piece;

import engine.Directions;

public class ExtendedRookModel extends ExtendedPieceModel {

    public ExtendedRookModel(int side) {
        super(PieceType.ROOK, side, 4);
        Directions.getOrthoDirections(allowedMovement);
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            increasedTileView = range;
            if (extendedSpecial) {
                propagateTileView = true;
                propagateViewInfluence(range);
            }
        } else {
            increasedTileView = 0;
            propagateTileView = false;
            propagateViewInfluence(0);
        }

    }

}