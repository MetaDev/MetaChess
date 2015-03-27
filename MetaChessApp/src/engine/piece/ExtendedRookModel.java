package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;
import static engine.piece.ExtendedPieceModel.specialIcon;

public class ExtendedRookModel extends ExtendedPieceModel {

    public ExtendedRookModel(ExtendedBoardModel board,int side) {
        super(PieceType.rook,board, side, 4);
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
