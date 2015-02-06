package engine.piece;

import engine.Directions;

public class ExtendedPawnModel extends ExtendedPieceModel {

    private boolean bound = false;

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public ExtendedPawnModel(int side) {
        super(PieceType.PAWN, side, 1);
        allowedMovement.add(Directions.getDirection(0, 1));
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        //when extended special is used, one can axis with a higher precision
        if (yes) {
            if (extendedSpecial) {
                this.axis = (this.axis + 1) * range % 8;

            } else {
                this.axis = (this.axis + 2) * range % 8;

            }
        }
        //no else because it's an cyclic variabel, so it doesn't return to default variabel on undecision
    }

    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {
        //use axis to alter direction
        return super.handleMovement(direction, 1, extendedSpecial);
    }

}
