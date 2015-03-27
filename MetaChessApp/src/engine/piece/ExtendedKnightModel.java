package engine.piece;

import engine.Directions;
import engine.board.ExtendedBoardModel;
import engine.board.ExtendedTileModel;
import java.util.List;
import meta.MetaConfig;

public class ExtendedKnightModel extends ExtendedPieceModel {

    public ExtendedKnightModel(ExtendedBoardModel board, int side) {
        super(PieceType.knight, board, side, 2);
        Directions.getKnightDirections(allowedMovement);
        specialIcon = "dragon";
    }
    private boolean killOnSecondTile;

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            killOnHooverTiles = range;
            if (extendedSpecial) {
                //set negative
                killOnSecondTile = true;
            }
        } //reset
        else {
            killOnHooverTiles = 0;
        }
    }

    //the knight handles movement differently
    @Override
    public boolean handleMovement(Directions.Direction direction, int range, boolean extendedSpecial) {

        // if dragon
        int i = direction.getX();
        int j = direction.getY();
        //if dragon always hoover
        boolean hoover = isDragon() || extendedSpecial;
        List<ExtendedTileModel> path1;
        List<ExtendedTileModel> path2;
        int firstHorMov;
        int firstVerMov;
        int lastHorMov;
        int lastVerMov;

        if (Math.abs(i) > Math.abs(j)) {
            firstHorMov = i;
            firstVerMov = 0;
            lastHorMov = 0;
            lastVerMov = j;
        } else {
            firstHorMov = 0;
            firstVerMov = j;
            lastHorMov = i;
            lastVerMov = 0;
        }
        //first the longest movement
        path2 = findPath(getTilePosition(), firstHorMov, firstVerMov, hoover);
        //check if path succeeded
        if (path2 == null) {
            return false;
        }
        //continue movement, last move never hoovers
        path1 = findPath(path2.get(1), lastHorMov, lastVerMov, false);
        //check if path exists
        if (path1 == null) {
            return false;
        }
        //add last tile to path
        path2.add(path1.get(0));
        return moveWithPath(path2);

    }

    //valid input for this method is the 3 tile path of the first part of the dragon movement
    private void dragonFire(ExtendedTileModel tile, int startFraction, int depth) {
        //kill pieces on hoover tile if dragon
        ExtendedPieceModel pieceOnHooveredTile;
        if (tile.getChildren() == null) {
            // if there's a piece on the hoovered tile
            pieceOnHooveredTile = board.getTilePiece(tile);
            //take if from opposite tile
            takePiece(pieceOnHooveredTile);

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
                    ExtendedPieceModel pieceOnHooveredTile = board.getTilePiece(tile.getChildren()[i][j]);
                    takePiece(pieceOnHooveredTile);
                    //continue recursion
                    recursiveTileKill(tile.getChildren()[i][j], startFraction, depth);

                }
            }
        }
    }

    @Override
    protected boolean checkPath(List<ExtendedTileModel> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            ExtendedPieceModel pieceOnPath = board.getTilePiece(path.get(i));
            pieceOnPath = board.getTilePiece(path.get(i));
            //if path occupied, bad path
            if (pieceOnPath != null) {
                //killed by fire
                if (pieceOnPath.onFire) {
                    ExtendedPieceModel fireMan = pieceOnPath.commander;
                    //find owner of firewall
                    if (pieceIsDeadly(fireMan)) {
                        board.pieceTaken(fireMan, this);
                    }
                    //no movement made, only stop movement if on fire
                    return false;
                }
            }
            if (isDragon()) {
                //the dragon fire kill happens recursively on the first tile of long path
                //handle dragon 
                if (i == 0) {
                    dragonFire(path.get(i), getTilePosition().getAbsFraction(), (int) Math.pow(2, Math.ceil(killOnHooverTiles / 2)));
                }
                //if extended special k
                if (killOnSecondTile && i == 1) {
                    dragonFire(path.get(i), getTilePosition().getAbsFraction(), (int) Math.pow(2, killOnHooverTiles / 2));
                }
            }
        }
        return true;
    }
}
