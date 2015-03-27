package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;
import engine.board.ExtendedTileModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExtendedBishopModel extends ExtendedPieceModel {

    private boolean wallsOnFire;
    private List<ExtendedBischopPawn> tail = new LinkedList<>();

    private List<ExtendedTileModel> lastPath = new LinkedList<>();

    public ExtendedBishopModel(ExtendedBoardModel board, int side) {
        super(PieceType.bischop, board, side, 4);
        Directions.getDiagDirections(allowedMovement);
        //add 8 bischop pawns to tail
        for (int i = 0; i < 8; i++) {
            tail.add(new ExtendedBischopPawn(board));
        }
        specialIcon = "runner";
    }

    //the last x tiles visited will be set occupied or on fire
    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            if (extendedSpecial) {
                wallsOnFire = true;
            }
            nrOfWallTiles = range;
        } else {
            nrOfWallTiles = 0;
            wallsOnFire = false;
            tail.clear();
        }

    }

    private void addToTail(List<ExtendedTileModel> path) {

        //traverse path in reverse order
        for (int i = path.size() - 1; i > 0; i--) {
            //add to the back
            lastPath.add(path.get(i));
            //keep correct size, remove first tile
            if (lastPath.size() > nrOfWallTiles) {
                lastPath.remove(0);
            }
        }
    }

    @Override
    protected boolean checkPath(List<ExtendedTileModel> path) {
        if (super.checkPath(path)) {
            if (nrOfWallTiles > 0) {
                addToTail(path);
                //update position of pieces in tail
                resetPositionTail();
            }
            return true;
        }

        return false;
    }

    private void resetPositionTail() {
        for (int i = 0; i < tail.size(); i++) {
            if (i < lastPath.size()) {
                board.changePiecePosition(tail.get(i), lastPath.get(i));

            } else {
                board.changePiecePosition(tail.get(i), null);
            }
        }
    }
}
