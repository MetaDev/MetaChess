package engine.piece;

import engine.Directions;
import meta.MetaConfig;

public class ExtendedBishopModel extends ExtendedPieceModel {


    

    public ExtendedBishopModel(int side) {
        super(PieceType.bischop, side, 4);
         Directions.getDiagDirections(allowedMovement);
        specialIcon = "runner";
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            if (extendedSpecial) {
                //double allowed moves per turn?
            }
            increasedMovementRange = range;
        } else {
            increasedMovementRange = 0;
        }

    }

    

}
