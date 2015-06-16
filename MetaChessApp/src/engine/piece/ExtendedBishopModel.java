package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;
import engine.board.ExtendedTileModel;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ExtendedBishopModel extends ExtendedPieceModel {

    private List<ExtendedBischopPawn> tail = new LinkedList<>();

    private List<ExtendedTileModel> lastPath = new LinkedList<>();

    public ExtendedBishopModel(ExtendedBoardModel board, int side) {
        super(PieceType.bischop, board, side, 4);
        Directions.getDiagDirections(allowedMovement);
        //add 8 bischop pawns to tail
        for (int i = 0; i < 8; i++) {
            tail.add(new ExtendedBischopPawn(board,this));
        }
        specialIcon = "runner";
    }

    //the last x tiles visited will be set occupied or on fire
    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            if (extendedSpecial) {
                onFire = true;
            }
            nrOfWallTiles = range;
        } else {
            nrOfWallTiles = 0;
            onFire = false;
            lastPath.clear();
        }
        resetPositionTail();

    }

    private void addTileToTail(ExtendedTileModel tile) {
        //add to to front
        lastPath.add(0, tile);
        //keep correct size, remove last tile
        if (lastPath.size() > nrOfWallTiles) {
            lastPath.remove(lastPath.size() - 1);
        }
    }

    private void addPathToTail(List<ExtendedTileModel> path) {

        //traverse path order of closest tile (last added to path) first, including the tile bischop is currently standing on
        for (int i = path.size() - 2; i >= 0; i--) {
            addTileToTail(path.get(i));
        }
    }

    @Override
    protected boolean moveWithPath(List<ExtendedTileModel> path) {
        //save old tile position and add as first to lastPath
        ExtendedTileModel oldTile = getTilePosition();
        boolean pathMoved = super.moveWithPath(path);
        if (pathMoved && nrOfWallTiles > 0) {
            addTileToTail(oldTile);
            addPathToTail(path);
            System.out.println("tail k. " + path.size() + " nrof wall: " + nrOfWallTiles);
        }
        return pathMoved;
    }

    private void resetPositionTail() {
        for (int i = 0; i < tail.size(); i++) {
            if (i < lastPath.size()) {
                System.out.println("position of bischop pawn changed");
                board.changePiecePosition(tail.get(i), lastPath.get(i));
            } else {
                board.changePiecePosition(tail.get(i), null);
            }
        }
    }

    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {
        boolean moved = super.handleMovement(direction, range, extendedSpecial);
        if (moved) {
            System.out.println("reset tail" + lastPath.size());
            resetPositionTail();
        }
        return moved;

    }
}
