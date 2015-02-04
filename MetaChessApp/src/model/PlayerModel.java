package model;

import engine.Directions.Direction;

import logic.BoardLogic;
import meta.MetaClock;
import meta.MetaConfig;

//contains all info about player
public class PlayerModel {

    protected int cooldown;
    protected int costOfActiveDecision;
    protected int absFractionOfActiveDecision;

    protected boolean locked = false;
    protected int absTime = 0;
    protected int previousFraction = 8;

    protected int[][] core;
    protected int range=1;
    protected boolean extendedSpecial = false;

    //if this is true the player can get pushed out of it's controlling piece without needing extended special when switching
    protected boolean pushable = false;

    public ExtendedPieceModel getControlledModel() {
        return controlledModel;
    }

    public void setControlledModel(ExtendedPieceModel controlledModel) {
        this.controlledModel = controlledModel;
//        // unbind if current piece is a pawn and it is bound
//        if (controlledModel.getType() == PieceType.PAWN && ((ExtendedPawnModel) controlledModel).isBound()) {
//            ((ExtendedKingModel) MetaConfig.getBoardModel()
//                    .getPieceByTypeAndSide(PieceType.KING, getSide()))
//                    .removePawnFromWall((ExtendedPawnModel) controlledModel);
//        }

    }

    protected String name;
    // the model the player is in
    protected ExtendedPieceModel controlledModel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerModel(int side, ExtendedPieceModel controlledModel,
            String name, int[][] core) {
        this.controlledModel = controlledModel;
        this.name = name;
        this.core = core;
    }

    // the side of the player is what side the first piece of control is
    public int getSide() {
        return controlledModel.getSide();
    }

    // a mode can be defined, for switch, like switch to nearest, furthest or a
    // priorityqueue with types can be made
    // will be implemented later
    // you can't switch to piece occupied by another player
    // the switch algorythm is defined by the range given
    // if mode=0 this means the switch key has been released, mode=1 is the
    // switch key pressed
    public void switchPiece() {
        double compareDist;
        double tempDist;
        ExtendedPieceModel newPiece = null;
        PlayerModel newPieceOwner = null;
        ExtendedPieceModel piece;
        //if the found piece is occupied, only switch is push is true, which makes the previous player switching to the next piece
        boolean push = isExtendeSpecial();
        // closest
        if (range == 1) {
            compareDist = Double.MIN_VALUE;
            for (PlayerModel player : MetaConfig.getBoardModel()
                    .getPlayersOnBoard()) {
                piece = player.getControlledModel();
                if (!controlledModel.equals(piece)
                        && (tempDist = BoardLogic.calculateDistance(
                                controlledModel.getTilePosition(),
                                piece.getTilePosition())) < compareDist) {
                    newPiece = piece;
                    compareDist = tempDist;
                    newPieceOwner = player;
                }
            }
        } // furthest
        else if (range == 2) {
            compareDist = Double.MAX_VALUE;
            for (PlayerModel player : MetaConfig.getBoardModel()
                    .getPlayersOnBoard()) {
                piece = player.getControlledModel();
                if (!controlledModel.equals(piece)
                        && (tempDist = BoardLogic.calculateDistance(
                                controlledModel.getTilePosition(),
                                piece.getTilePosition())) < compareDist) {
                    newPiece = piece;
                    compareDist = tempDist;
                    newPieceOwner = player;
                }
            }
        }
        //check if player is pushable if not check if push activated
        if (newPiece != null && newPieceOwner != null) {
            if (newPieceOwner.pushable) {
                newPieceOwner.setControlledModel(controlledModel);
                setControlledModel(newPiece);
            } else if (push) {
                newPieceOwner.setControlledModel(controlledModel);
                setControlledModel(newPiece);
            }

        }

    }
//check if turn changed, decrease or increase cooldown
    //if the turn calculated from current is different from turn calculated from the abs time from the latest registered turnchange

    protected boolean turnChanged() {

        ExtendedTileModel tile = controlledModel.getTilePosition();
        return MetaClock.getTurn(tile.getAbsFraction(), getSide()) != MetaClock
                .getTurn(tile.getAbsFraction(), getSide(), getAbsTime());

    }
//no decision is made here
    //in player subtypes decide is overriden and used to decide whether a special or movement is made or not e.g. based on input in input player

    public void decide() {
        if (turnChanged()) {
            turnChange();
        }
    }

    protected boolean playerIsOverMaxFraction() {
        return controlledModel.getTilePosition().getAbsFraction() >= MetaClock.getMaxFraction();
    }
 private boolean hasTurn(){
     return MetaClock.getTurn(this);
 }
    protected void handleMovement(Direction direction) {
        //don't lock for movement once passed max fraction
        if (hasTurn() && (!locked || playerIsOverMaxFraction())) {
            controlledModel.step(direction, range, extendedSpecial);
            locked = true;
        }
    }

    public void handleSpecial(boolean on) {

        //if special decision check if no cooldown left 
        if (hasTurn() && cooldown == 0 && on) {
            //save cost of decision
            costOfActiveDecision = range;
            if (extendedSpecial) {
                costOfActiveDecision += 3;
            }
            //save fraction of position on decision
            absFractionOfActiveDecision = getControlledModel().getTilePosition().getAbsFraction();
            //raise cooldown
            cooldown = costOfActiveDecision;
            //execute on piece
            controlledModel.setSpecial(true, range, extendedSpecial);

        }
        if (!on) {
            costOfActiveDecision = 0;
            //execute on piece
            controlledModel.setSpecial(false, range, extendedSpecial);
        }

    }

    public int getRange() {
        return range;
    }

    //get total cooldown relative to fraction of position tile
    public int getRelativeCooldown() {
        int maxFraction = Math.min(controlledModel.getTilePosition().getAbsFraction(), MetaClock.getMaxFraction());
        return (maxFraction / MetaClock.getMaxFraction()) * cooldown;
    }

    public int getCooldown() {
        return cooldown;
    }

    // on the turn change, you should add cooldown if active decision
    // cooldown is paid after every turn, with the highest range in that turn
    // cooldown is payed after making decision, and while it stays active after
    // every turn change
    public void turnChange() {
        ExtendedTileModel position = controlledModel.getTilePosition();
        int newFraction = position.getAbsFraction();
        if (previousFraction >= newFraction && costOfActiveDecision == 0 && cooldown > 0) {
            cooldown -= getCooldown();
            //cooldown is min 0
            cooldown = Math.max(0, cooldown);
        }

        //if a decision is active 
        if (costOfActiveDecision > 0) {
            //raise cooldown
            cooldown += getCooldown();

        }
        // unlock player
        locked = false;
        absTime = MetaClock.getAbsoluteTime();
        previousFraction = newFraction;
    }

    public int[][] getCore() {
        return core;
    }

    public void setCore(int[][] core) {
        this.core = core;
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

    //each piece can extend it's special with another ability, this at the cost of 3 extra cooldown
    public void setExtendeSpecial(boolean yes) {
        extendedSpecial = yes;
    }

    public boolean isExtendeSpecial() {
        return extendedSpecial;
    }

}
