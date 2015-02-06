package engine.piece;

import engine.player.Player;
import engine.Directions.Direction;
import engine.MetaClock;
import java.util.HashSet;
import java.util.Set;
import engine.board.BoardLogic;

import meta.MetaConfig;
import engine.board.ExtendedTileModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ExtendedPieceModel {

    public enum PieceType {

        PAWN, ROOK, KNIGHT, BISHOP, KING, QUEEN
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

    //a very special var, if negative all tiles that are hoovered have a chance to kill piece on it
    // until a depth of 2^abs(killOnHooverTiles) lower than current depth
    //if it's non negative only one of the tiles hoovered is targeted
    protected int killOnHooverTiles = 0;
    protected boolean penetrateLowerFraction = false;
    protected PieceType type;
    //the icon for the special in the gui
    protected static int[] specialIcon;
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

    public boolean isPenetrateLowerFraction() {
        return penetrateLowerFraction;
    }

    public void setPenetrateLowerFraction(boolean penetrateLowerFraction) {
        this.penetrateLowerFraction = penetrateLowerFraction;
    }

    public int[][] getGrid() {
        return MetaConfig.getIcon(type.name());
    }

    public int getIncreasedTileview() {
        return increasedTileView;
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
        return penetrateLowerFraction || increasedMovementRange > 0;
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
        List<ExtendedTileModel> path = findPath(getTilePosition(),i, j, extendedSpecial);
        if (path == null) {
            return false;
        }
        //
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
        if (pieceOnnewTile == null) {
            // set new position for model
            setTilePosition(lastTileInPath);
            return true;
        } else if (pieceOnnewTile.getColor() != getColor()) {
            handlePieceTaken(pieceOnnewTile);
            System.out.println("piece killed");
            // set new position for model
            setTilePosition(lastTileInPath);
            return true;
        }

        // move not made
        return false;
    }

    protected boolean checkPath(List<ExtendedTileModel> path) {
        //check if path doesn't contain any other pieces
        for (int i = 0; i < path.size() - 2; i++) {
            if (MetaConfig.getBoardModel().getPieceByPosition(path.get(i)) != null) {
                return false;
            }
        }
        return true;
    }

    protected List<ExtendedTileModel> findPath(ExtendedTileModel previousTile,int i, int j, boolean extendedSpecial) {

        boolean hoover = extendedSpecial;
        List<ExtendedTileModel> path = new ArrayList<>();
        BoardLogic.findTilePath(previousTile,
                i, j, hoover,
                isPenetrateLowerFraction(), path);
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

  
    //set all non-type vars to initial value;
    //used when a player leaves a piece

    public void reset() {
//TODO
    }

    //here starts the logic for movement
    // handle logic when 2 pieces are on same tile, return of piece can make
    // move or not
    protected void handlePieceTaken(ExtendedPieceModel pieceOnnewTile) {
        if (MetaConfig.getBoardModel().getPlayerByPiece(pieceOnnewTile).isDecreaseLivesOnKill()) {
            //decrease side lives
            MetaConfig.getBoardModel().decreaseSideLives(pieceOnnewTile.getColor(),
                    pieceOnnewTile.getLives());

        }
        //put player on a random tile
        pieceOnnewTile.setTilePosition(BoardLogic.getRandomTile(
                MetaClock.getMaxFraction(), false));

    }

}
