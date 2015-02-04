package model;

import engine.Directions;
import engine.Directions.Direction;
import java.util.HashSet;
import java.util.Set;
import logic.BoardLogic;
import logic.PieceMovementLogic;

import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public abstract class ExtendedPieceModel {

    protected int color;
    protected int side;

    protected int lives;
    protected ExtendedTileModel position;
    protected int increaseTileView = 0;
    protected int increasedMovementRange = 0;
    // only the rook will adapt this
    protected boolean viewing = false;
    protected boolean typeVisible = true;

    protected boolean ignoreOccupationOfTile = false;
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
        //nr of tiles seen is max half the absolute fraction of the tile standong on
        //if viewing activated add range to 
        return Math.min(8 + increaseTileView,
                getTilePosition().getAbsFraction() / 2);
    }

    public void setInceasedTileview(int param) {
        increaseTileView = param;
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

    
    //to be overriden
    public Set<DecisionModel> getAllowedDecision() {
        return null;
    }

    public int getIncreasedTileview() {
        return increaseTileView;
    }

    public void propagateViewInflunce(int increasedTileView) {
        //for each piece in view, pass increased tile view
        for (PlayerModel player : MetaConfig.getBoardModel().getPlayersOnBoard()) {
            ExtendedPieceModel piece = player.getControlledModel();
            if (piece.getIncreasedTileview() == 0 && BoardLogic.isInrange(piece, this)) {
                piece.setInceasedTileview(increasedTileView);
                //proipagate
                piece.propagateViewInflunce(increasedTileView);
            }
        }
    }

    public boolean isIgnoreOccupationOfTile() {
        return ignoreOccupationOfTile;
    }

    public void setIgnoreOccupationOfTile(boolean ignoreOccupationOfTile) {
        this.ignoreOccupationOfTile = ignoreOccupationOfTile;
    }

    public int getLives() {
        return lives;
    }

    public ExtendedPieceModel(PieceType type, int side, int lives) {
        this.side = side;
        this.color = side;
        this.type = type;
        this.lives = lives;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    

    public float getRelSize() {
        return position.getRelSize();
    }

    public ExtendedTileModel getTilePosition() {
        return position;
    }

    public void setTilePosition(ExtendedTileModel position) {
        this.position = position;
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

    protected int turn = 0;

    //this method is overriden in all piece and implements the change in vars form the special
    public abstract void setSpecial(boolean yes, int range, boolean extendedSpecial);

    //overriden method
    //the type of the piece is decisive for the way movement is handled and also the state of the pieve variables
    public void step(Direction direction, int range, boolean extendedSpecial) {
        //extended special decides hoover
        PieceMovementLogic.movement(direction.getX() * range, direction.getY() * range, this,extendedSpecial, isIgnoreOccupationOfTile());

    }

    public int getCommand() {
        return 0;
    }

    public int getTurn() {
        return turn;
    }

    //set all non-type vars to initial value;
    public void reset() {

    }

}
