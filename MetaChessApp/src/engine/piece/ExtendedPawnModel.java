package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;

public class ExtendedPawnModel extends ExtendedPieceModel {


    public boolean isBound() {
        return commander!=null;
    }

    public void setBound(ExtendedKingModel commander) {
        this.commander = commander;
    }

    public ExtendedPawnModel(ExtendedBoardModel board,int side) {
        super(PieceType.pawn,board, side, 1);
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
        }else{
            this.axis=0;
        }
       
    }

    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {
        //use axis to alter direction
        boolean succeeded = super.handleMovement(Directions.turnDirection(direction, axis), 1, extendedSpecial);
        if (succeeded && commander!=null) {
            ((ExtendedKingModel)commander).removePawnFromWall(this);
            commander=null;
        }
        return succeeded;
    }

}
