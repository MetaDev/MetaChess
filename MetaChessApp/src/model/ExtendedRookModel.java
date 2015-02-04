package model;

import engine.Directions;
import meta.MetaConfig.PieceType;

public class ExtendedRookModel extends ExtendedPieceModel {

    public ExtendedRookModel(int side) {
        super(PieceType.ROOK, side, 4);
        allowedMovement.add(Directions.getDirection(1, 0));
        allowedMovement.add(Directions.getDirection(0, 1));
        allowedMovement.add(Directions.getDirection(-1, 0));
        allowedMovement.add(Directions.getDirection(0, -1));

    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            if (extendedSpecial) {
                //propagate tile view influence
                propagateViewInflunce(range);
            }
            increaseTileView = range;
        } else {
            increaseTileView = 0;
        }

    }

}
