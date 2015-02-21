package engine.piece;

import engine.Directions;
import static engine.piece.ExtendedPieceModel.specialIcon;
import meta.MetaConfig;

public class ExtendedRookModel extends ExtendedPieceModel {

    public ExtendedRookModel(int side) {
        super(PieceType.rook, side, 4);
        Directions.getOrthoDirections(allowedMovement);
        specialIcon = "horizon";

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
