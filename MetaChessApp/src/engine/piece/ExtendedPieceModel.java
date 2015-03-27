package engine.piece;

import engine.player.Player;
import engine.Directions.Direction;
import java.util.HashSet;
import java.util.Set;
import engine.board.BoardLogic;
import engine.board.ExtendedBoardModel;

import engine.board.ExtendedTileModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPieceModel {

    protected ExtendedPieceModel commander;

    public enum PieceType {

        pawn, rook, knight, bischop, king, queen
    }

    protected float color;
    protected boolean onFire = false;
    protected int lives;
    protected boolean propagateTileView = false;
    protected int increasedTileView = 0;
    protected int nrOfWallTiles = 0;
    // only the rook will adapt this
    protected boolean viewing = false;
    protected boolean typeVisible = true;
    //is the piece controlled by the input player
    protected boolean bound;
    //a very special var, if negative all tiles that are hoovered have a chance to kill piece on it
    // until a depth of 2^abs(killOnHooverTiles) lower than current depth
    //if it's non negative only one of the tiles hoovered is targeted
    protected int killOnHooverTiles = 0;
    protected PieceType type;
    //the icon for the special in the gui
    protected static String specialIcon;
    //save icons as long, 64 bit bitstring
    //save icon of decision and type

    protected Set<Direction> allowedMovement = new HashSet<>();

    protected ExtendedBoardModel board;

    public Set<Direction> getAllowedMovement() {
        return allowedMovement;
    }

    public int getNrOfViewTiles() {
        //nr of tiles seen is max half the absolute fraction of the tile standing on
        //if viewing activated add range to 
        return Math.min(8 + increasedTileView,
                getTilePosition().getAbsFraction() / 2);
    }

    public void setInceasedTileview(int param) {
        increasedTileView = param;
    }

    public PieceType getType() {
        return type;
    }

    public int getIncreasedTileview() {
        return increasedTileView;
    }

    public String getName() {
        return type.name();
    }

    public void propagateViewInfluence(int range) {
        for (Player player : board.getPlayersOnBoard()) {
            ExtendedPieceModel piece = player.getControlledModel();
            //don't increase for self, out of range or already increased
            if (!piece.equals(this) && piece.getIncreasedTileview() == 0 && BoardLogic.isInrange(this, piece)) {
                piece.setInceasedTileview(range);
            } else if (piece.getIncreasedTileview() > 0 && !BoardLogic.isInrange(piece, this)) {
                piece.setInceasedTileview(0);
            }
        }
    }

    public int getLives() {
        return lives;
    }

    public ExtendedPieceModel(PieceType type, ExtendedBoardModel board, float color, int lives) {
        this.board = board;
        this.color = color;
        this.type = type;
        this.lives = lives;
    }

    public float getColor() {
        return color;
    }

    

    public float getRelSize() {
        return getTilePosition().getRelSize();
    }

    public ExtendedTileModel getTilePosition() {
        return board.getPieceTile(this);
    }

    public void setTilePosition(ExtendedTileModel position) {
        board.changePiecePosition(this, position);
    }

    public boolean isDragon() {
        return killOnHooverTiles > 0;
    }

    public boolean isRunner() {
        return nrOfWallTiles > 0;
    }

    public boolean getViewing() {
        return viewing;
    }

    protected int axis = 0;

    //this method is overriden in all piece and implements the change in vars form the special
    public abstract void setSpecial(boolean yes, int range, boolean extendedSpecial);

    //overriden method
    //the type of the piece is decisive for the way movement is handled and also the state of the pieve variables
    public boolean handleMovement(Direction direction, int range, boolean extendedSpecial) {
        int i = direction.getX() * range;
        int j = direction.getY() * range;
        List<ExtendedTileModel> path = findPath(getTilePosition(), i, j, extendedSpecial);
        if (path == null) {
            return false;
        }

        return moveWithPath(path);
    }

    protected boolean moveWithPath(List<ExtendedTileModel> path) {
        //chech if path and last tile are valid

        ExtendedTileModel lastTile = path.get(path.size() - 1);
        //remove last tile from path
        path.remove(path.size() - 1);
        //first check last tile for move to be made, than check path
        if (checkLastTileInPath(lastTile) && checkPath(path)) {
            handleLastTileInPath(lastTile);
            return true;
        }
        return false;
    }

    //check if movement can be made
    protected boolean checkLastTileInPath(ExtendedTileModel lastTileInPath) {
        ExtendedPieceModel pieceOnnewTile = board
                .getTilePiece(lastTileInPath);
        //if not occupied continue
        if (pieceOnnewTile == null) {
            return true;
        } //if occupied only continue movement if piece can be taken
        else if ( pieceIsDeadly(pieceOnnewTile)) {
            return true;
        }
        return false;

    }

    //handle movement on last tile, and set position
    protected boolean handleLastTileInPath(ExtendedTileModel lastTileInPath) {
        ExtendedPieceModel pieceOnnewTile = board
                .getTilePiece(lastTileInPath);
        if (pieceIsDeadly(pieceOnnewTile)) {
            if (pieceOnnewTile.onFire) {
                ExtendedPieceModel fireMan = pieceOnnewTile.commander;
                //find owner of firewall
                board.pieceTaken(fireMan, this);
                //no movement made
                return false;
            }
            //not on fire so kill it
            board.pieceTaken(this, pieceOnnewTile);

        }
        setTilePosition(lastTileInPath);
        return true;
    }

    //check if path can be taken
    protected boolean checkPath(List<ExtendedTileModel> path) {
        ExtendedPieceModel pieceOnPath;
        //check if path (all tiles except last) doesn't contain any other pieces
        for (int i = 0; i < path.size() - 1; i++) {
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

                }
                //no movement made
                return false;
            }
        }

        return true;
    }

    protected List<ExtendedTileModel> findPath(ExtendedTileModel startTile, int i, int j, boolean extendedSpecial) {
        //use direction coordinates to construct a tile path
        int verMov;
        int horMov;
        int remainingHorMov = i;

        int remainingVerMov = j;
        boolean hoover = extendedSpecial;
        List<ExtendedTileModel> path = new ArrayList<>();
        ExtendedTileModel previousTile = startTile;
        int startFraction = startTile.getAbsFraction();
        //continue while movement left
        while (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) > 0) {
            //calculate singel tile directions
            verMov = Integer.signum(j);
            horMov = Integer.signum(i);
            //decrease remaining movement
            remainingHorMov = remainingHorMov - horMov;
            remainingVerMov = remainingVerMov - verMov;
            //on the last step of the movement hoover is always false
            if (Math.abs(remainingHorMov) + Math.abs(remainingVerMov) == 0) {
                previousTile = board.findTileNeighBour(previousTile,
                        horMov, verMov, false, startFraction);

            } else {
                previousTile = board.findTileNeighBour(previousTile,
                        horMov, verMov, hoover, startFraction);

            }
            if (previousTile == null) {
                return null;
            }
            path.add(previousTile);

        }

        ExtendedTileModel lastTile = path.get(path.size() - 1);
        // movement did not succeed
        if (lastTile == null) {
            return null;
        }
        return path;
    }

    public ExtendedBoardModel getBoard() {
        return board;
    }

    public int getAxis() {
        return axis;
    }

    protected boolean pieceIsDeadly(ExtendedPieceModel piece) {
        //check if player in piece can be killed
        return piece != null && piece.getColor() != getColor();
    }

    protected void takePiece(ExtendedPieceModel pieceOnnewTile) {
        board.pieceTaken(pieceOnnewTile, this);
    }

}
