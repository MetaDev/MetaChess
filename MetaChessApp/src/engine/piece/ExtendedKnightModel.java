package engine.piece;

import engine.Directions;
import engine.board.BoardLogic;
import engine.board.ExtendedTileModel;
import java.util.ArrayList;
import java.util.List;
import meta.MetaConfig;

public class ExtendedKnightModel extends ExtendedPieceModel {

    public ExtendedKnightModel(int side) {
        super(PieceType.KNIGHT, side, 2);
        Directions.getKnightDirections(allowedMovement);
        //for knight movement penetrate lower fraction is always true
        penetrateLowerFraction = true;
    }

    @Override
    public int[][] getGrid() {
        if (isDragon()) {
            return MetaConfig.getIcon("DRAGON");
        } else {
            return super.getGrid();
        }
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            killOnHooverTiles = range;
            if (extendedSpecial) {
                //set negative
                killOnHooverTiles *= -1;
            }
        } //reset
        else {
            killOnHooverTiles = 0;
        }
    }

    //the knight handles movement differently
    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {

        //if not dragon movement stays about the same
        if (!isDragon()) {
            super.handleMovement(direction, range, extendedSpecial);
        }
        // if dragon
        int i = direction.getX();
        int j = direction.getY();
        //if dragon always hoover
        boolean hoover = isDragon();
        List<ExtendedTileModel> path1 = new ArrayList<>();
        List<ExtendedTileModel> path2 = new ArrayList<>();
        ExtendedTileModel previousTile = getTilePosition();
        //calculate paths of hor and vert movement seperately if dragon
        if (Math.abs(i) == 2) {
            //first the longest movement
            path2 = findPath(getTilePosition(), i + 1, 0, extendedSpecial);
            //check if path succeeded
            if (path2.get(1) == null) {
                return false;
            }
            //continue movement
            path1 = findPath(path2.get(1), i + 1, 0, extendedSpecial);

        } else {
            //first the longest movement
            path2 = findPath(getTilePosition(), 0, j + 1, extendedSpecial);
//check if path succeeded
            if (path2.get(1) == null) {
                return false;
            }
            //continue movement
            path1 = findPath(path2.get(1), i, 0, extendedSpecial);

        }
        ExtendedTileModel firstTile = path2.get(0);
        ExtendedTileModel intermediaryTile = path2.get(1);
        ExtendedTileModel lastTile = path1.get(path1.size() - 1);

        //handle last tile first to check if no problem occured
        if (!handleLastTileInPath(lastTile)) {
            return false;
        }
        //the dragon fire kill happens recursively on the first tile of long path
        //handle dragon fire
        dragonFire(firstTile, getTilePosition().getAbsFraction(), (int) Math.pow(2, Math.ceil(range / 2)));
        //if extended special k
        if (extendedSpecial) {
            dragonFire(intermediaryTile, getTilePosition().getAbsFraction(), (int) Math.pow(2, range / 2));
        }
        return true;
    }

    //valid input for this method is the 3 tile path of the first part of the dragon movement
    private void dragonFire(ExtendedTileModel tile, int startFraction, int depth) {
        //kill pieces on hoover tile if dragon
        ExtendedPieceModel pieceOnHooveredTile;
        if (tile.getChildren() == null) {
            // if there's a piece on the hoovered tile
            pieceOnHooveredTile = MetaConfig
                    .getBoardModel().getPieceByPosition(tile);
            //take if from opposite tile
            if (pieceOnHooveredTile != null && pieceOnHooveredTile.getColor() != getColor()) {
                handlePieceTaken(pieceOnHooveredTile);

            }
        } else // now check deeper fractions, recursively
        {
            recursiveTileKill(tile, startFraction, depth);
        }

    }

    private void recursiveTileKill(ExtendedTileModel tile, int startFraction, int depth) {
        if (tile.getChildren() != null && tile.getAbsFraction() / startFraction <= depth) {
            // iterate over all children
            for (int i = 0; i < tile.getChildFraction(); i++) {
                for (int j = 0; j < tile.getChildFraction(); j++) {
                    // if there's a piece on the hoovered tile
                    ExtendedPieceModel pieceOnHooveredTile = MetaConfig
                            .getBoardModel().getPieceByPosition(tile.getChildren()[i][j]);
                    handlePieceTaken(pieceOnHooveredTile);
                    //continue recursion
                    recursiveTileKill(tile.getChildren()[i][j],startFraction,depth);

                }
            }
        }
    }

    @Override
    protected boolean checkPath(List<ExtendedTileModel> path) {
        return true;
    }
}
