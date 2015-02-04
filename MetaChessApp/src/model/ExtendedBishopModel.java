package model;

import engine.Directions;
import engine.Directions.Direction;
import meta.MetaConfig.PieceType;

public class ExtendedBishopModel extends ExtendedPieceModel {


    

    public ExtendedBishopModel(int side) {
        super(PieceType.BISHOP, side, 4);
        allowedMovement.add(Directions.getDirection(1,1));
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

    @Override
    public void step(Direction direction, int range, boolean extendedSpecial) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
