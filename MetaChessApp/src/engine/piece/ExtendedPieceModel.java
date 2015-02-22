package engine.piece;

import engine.player.Player;
import engine.Directions.Direction;
import java.util.HashSet;
import java.util.Set;
import engine.board.BoardLogic;

import meta.MetaConfig;
import engine.board.ExtendedTileModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPieceModel {

    public enum PieceType {

        pawn, rook, knight, bischop, king, queen
    }

    protected int color;

    protected int lives;
    protected ExtendedTileModel position;
    protected boolean propagateTileView = false;
    protected int increasedTileView = 0;
    protected int increasedMovementRange = 0;
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
        for (Player player : MetaConfig.getBoardModel().getPlayersOnBoard()) {
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

    public ExtendedPieceModel(PieceType type, int color, int lives) {
        this.color = color;
        this.type = type;
        this.lives = lives;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getRelSize() {
        return position.getRelSize();
    }

    public ExtendedTileModel getTilePosition() {
        return position;
    }

    public void setTilePosition(ExtendedTileModel position) {
        this.position = position;
        if (propagateTileView) {
            propagateViewInfluence(increasedTileView);
        }
    }

    public boolean isDragon() {
        return killOnHooverTiles > 0;
    }

    public boolean isRunner() {
        return increasedMovementRange > 0;
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
        //chech if all but last tile is occupied by any piece
        if (!checkPath(path)) {
            return false;
        }
        ExtendedTileModel lastTile = path.get(path.size() - 1);
        return handleLastTileInPath(lastTile);

    }

    protected boolean handleLastTileInPath(ExtendedTileModel lastTileInPath) {
        // if there's a piece on the new tile
        ExtendedPieceModel pieceOnnewTile = MetaConfig.getBoardModel()
                .getPieceByPosition(lastTileInPath);
        if (pieceCanBeTaken(pieceOnnewTile)) {
            takePiece(pieceOnnewTile);
        } else if (pieceOnnewTile != null) {
            return false;
        }
        setTilePosition(lastTileInPath);

        return true;
    }

    protected boolean checkPath(List<ExtendedTileModel> path) {
        //check if path (all tiles except last) doesn't contain any other pieces
        for (int i = 0; i < path.size() - 2; i++) {
            if (path.get(i).isOccupied()) {
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
                previousTile = BoardLogic.findTileNeighBour(previousTile,
                        horMov, verMov, false, startFraction);

            } else {
                previousTile = BoardLogic.findTileNeighBour(previousTile,
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

    public int getAxis() {
        return axis;
    }

    protected boolean pieceCanBeTaken(ExtendedPieceModel piece) {
        //check if player in piece can be killed
        return piece != null && piece.getColor() != getColor();
    }

    protected void takePiece(ExtendedPieceModel pieceOnnewTile) {
        MetaConfig.getBoardModel().pieceTaken(pieceOnnewTile, this);
    }

}
