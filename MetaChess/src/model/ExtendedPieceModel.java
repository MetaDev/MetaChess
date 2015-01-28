package model;

import java.util.Set;
import logic.BoardLogic;

import logic.DecisionExecutionLogic;
import meta.MetaClock;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;
import meta.MetaWindow;
import org.lwjgl.glfw.GLFW;

public class ExtendedPieceModel {

    public void step(int[] direction) {
        if (type == PieceType.KING) {
            ExtendedKingModel king = (ExtendedKingModel) this;
            DecisionExecutionLogic.kingMovement(direction, king);
        } else {
            DecisionExecutionLogic.movement(direction[0] * getMovementRange(), direction[1] * getMovementRange(), this, isIgnoreOccupationOfTile());
        }
    }

    protected int color;
    protected int side;
    protected int absTime = 0;
    private int lives;
    private int previousFraction = 8;
    protected int increaseTileView = 0;
    protected int increasedMovementRange = 0;
    // only the rook will adapt this
    protected boolean viewing = false;
    protected boolean extendedSpecial = false;
    protected boolean hoover = false;

    public boolean isHoover() {
        return hoover;
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

    protected int range = 1;

    protected boolean ignoreOccupationOfTile = false;
    protected boolean killOnHooverByChance = false;
    protected boolean killOnHoover = false;
    protected boolean penetrateLowerFraction = false;
    protected PieceType type;

    public PieceType getType() {
        return type;
    }

    protected int cooldown;
    protected int rangeOfActiveDecsion;
    protected int absFractionOfActiveDecision;
    protected SpecialDecisionModel activeDecision;

    // again
    protected boolean locked = false;

    public boolean isPenetrateLowerFraction() {
        return penetrateLowerFraction;
    }

    public void setPenetrateLowerFraction(boolean penetrateLowerFraction) {
        this.penetrateLowerFraction = penetrateLowerFraction;
    }

    // on the turn change, you should add cooldown if active decision
    // cooldown is paid after every turn, with the highest range in that turn
    // cooldown is payed after making decision, and while it stays active after
    // every turn change
    public void turnChange() {

        ExtendedTileModel position = getTilePosition();
        int newFraction = position.getAbsFraction();
        if (previousFraction >= newFraction && activeDecision == null) {
            cooldown -= getCooldown();
            //cooldown is min 0
            cooldown = Math.max(0, cooldown);
        }

        //if a decision is active 
        if (activeDecision != null) {
            //raise cooldown
            cooldown += getCooldown();
            //and reexecute special decision
            activeDecision.getSpecialObject().setParam(this);
        }
        // unlock piece
        locked = false;
        absTime = MetaClock.getAbsoluteTime();
        previousFraction = newFraction;
    }

    public int[][] getGrid() {
        return MetaConfig.getIcon(type.name());
    }

    public int getMovementRange() {
        return getRange();
    }

    //to be overriden
    public Set<DecisionModel> getAllowedDecision() {
        return null;
    }

    public int getIncreasedTileview() {
        return increaseTileView;
    }

    public void handleDecision(DecisionModel decision) {
        //decision is movement
        if (decision.isLocks()) {
            if (!locked) {
                //1 is pressed 0 up
                if (decisionMade(decision)) {
                    locked = true;
                    step(((MovementDecisionModel) decision).getDirection());
                }
                
            }
            return;
        }
        //if special decision check if no cooldown left and not active
        //if range (no cooldown not locking) change it
        if (!decision.isCooldown() || (cooldown == 0 && !decision.equals(activeDecision))) {
            //execute param object
            ((SpecialDecisionModel) decision).getSpecialObject().setParam(this);
            //raise cooldown and set active decisoion
            if (decision.isCooldown()) {
                cooldown += getCooldown();
                activeDecision = (SpecialDecisionModel) decision;
            }

        }

    }

    public int getCooldown() {
        int maxFraction = Math.min(getTilePosition().getAbsFraction(), MetaClock.getMaxFraction());
        return (maxFraction / MetaClock.getMaxFraction()) * rangeOfActiveDecsion;
    }

    public void update() {
        //reset parameters
        range = 1;
        penetrateLowerFraction = false;
        increaseTileView = 0;
        increasedMovementRange = 0;
        hoover = false;
        killOnHoover = false;
        killOnHooverByChance = false;
        extendedSpecial = false;
        //check if key of active decision is still
        if (activeDecision != null && !decisionMade(activeDecision)) {
            activeDecision = null;
        }
        //decisions handled by priority range , special , movement
        for (DecisionModel decision : this.getAllowedDecision()) {
            //1 is pressed 0 up
            if (decisionMade(decision)) {
                handleDecision(decision);
            }

        }

        //check if turn changed, decrease or increase cooldown
        //if the turn calculated from current is different from turn calculated from the abs time from the latest registered turnchange
        ExtendedTileModel tile = getTilePosition();
        if (MetaClock.getTurn(tile.getAbsFraction(), getSide()) != MetaClock
                .getTurn(tile.getAbsFraction(), getSide(), getAbsTime())) {
            turnChange();
        }
        // TODO: here can be saved wether a team makes at least 1 decision every

    }

    public void propagateViewInflunce(int increasedTileView) {

        //for each piece in view, pass increased tile view
        for (ExtendedPieceModel piece : MetaConfig.getBoardModel().getEntityModels().keySet()) {
            if (piece.getIncreasedTileview() == 0 && BoardLogic.isInrange(piece, this)) {
                piece.setInceasedTileview(increasedTileView);
                //proipagate
                piece.propagateViewInflunce(increasedTileView);
            }
        }
    }
//check if decision is made iether by AI, player or nothing

    public boolean decisionMade(DecisionModel decision) {
        //if it's a player 
        if (MetaConfig.getBoardModel().getPlayer().getControlledModel().equals(this)) {
            if (1 == GLFW.glfwGetKey(MetaWindow.getWindow(), decision.getKey())) {
                return true;
            }
        }
        //here comes code for AI
        return false;
    }

    public int getAbsTime() {
        return absTime;
    }

    public void setAbsTime(int absTime) {
        this.absTime = absTime;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
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

    // the range is ranged between 1 and 8 + influencedRange
    public int getRange() {
        return range;
    }

    // if range is set negative the directions are inverted
    public void setRange(int range) {
        this.range = range;

    }

    public float getRelSize() {
        return MetaConfig.getBoardModel().getPiecePosition(this).getRelSize();
    }

    public ExtendedTileModel getTilePosition() {
        return MetaConfig.getBoardModel().getPiecePosition(this);
    }

    public void setTilePosition(ExtendedTileModel tile) {
        MetaConfig.getBoardModel().setPiecePosition(this, tile);
    }

    public boolean isDragon() {
        return killOnHooverByChance;
    }

    public void setDragon() {
        if (extendedSpecial) {
            //set var killAllHooverTles to true
            killOnHoover = true;
        }
        killOnHooverByChance = true;
    }

    public boolean isRogue() {
        return penetrateLowerFraction || increasedMovementRange > 0;
    }

    public void setRogue() {
        if (extendedSpecial) {
            penetrateLowerFraction = true;
        }
        increasedMovementRange = getRange();
    }

    public boolean getViewing() {
        return viewing;
    }

    public void setViewing() {
        if (extendedSpecial) {
            //propagate tile view influence
            propagateViewInflunce(getRange());
        }
        increaseTileView = getRange();
    }
    private int turn = 0;

    public void setTurn() {
        this.turn = (this.turn + 2) % 8;
    }

    public void setExtendeSpecial() {
        extendedSpecial = true;
    }

    public boolean isExtendeSpecial() {
        return extendedSpecial;
    }

    public int getTurn() {
        return turn;
    }

    public int getCommand() {
        return 0;
    }

    public void setCommand() {

    }

}
