package engine.player;

import engine.Directions.Direction;

import engine.board.BoardLogic;
import engine.MetaClock;
import engine.piece.ExtendedPieceModel;
import engine.board.ExtendedTileModel;
import meta.MetaConfig;

//contains all info about player
public class Player {

    protected int cooldown;
    protected int rangeOfActiveDecision;
    protected boolean extendeRangeOfActiveDecision;
    protected int absFractionOfActiveDecision;
    protected int side;
    protected boolean decreaseLivesOnKill = false;
    protected boolean locked = false;
    protected int absTime = 0;
    protected int previousFraction = 8;
    //boolean to distinguish between input player
    protected String core;
    protected int range = 1;
    protected boolean extendedSpecial = false;

    //if this is true the player can get pushed out of it's controlling piece without needing extended special when switching
    protected boolean pushable = false;

    public ExtendedPieceModel getControlledModel() {
        return controlledModel;
    }

    public boolean isDecreaseLivesOnKill() {
        return decreaseLivesOnKill;
    }

    public void setDecreaseLivesOnKill(boolean decreaseLivesOnKill) {
        this.decreaseLivesOnKill = decreaseLivesOnKill;
    }

    public void setControlledModel(ExtendedPieceModel controlledModel) {
        if (controlledModel.getColor() != side) {
            System.out.println("Trying to bind player from opposite side to piece");
        }
        this.controlledModel = controlledModel;

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

    public Player(int side, ExtendedPieceModel controlledModel,
            String name, String core) {
        this.controlledModel = controlledModel;
        this.name = name;
        this.core = core;
        this.side = side;
    }

    // the side of the player is what side the first piece of control is
    public int getSide() {
        return side;
    }

    // a mode can be defined, for switch, like switch to nearest, furthest or a
    // priorityqueue with types can be made
    // will be implemented later
    // you can't switch to piece occupied by another player
    // the switch algorythm is defined by the range given
    // if mode=0 this means the switch key has been released, mode=1 is the
    // switch key pressed
    public boolean handleSwitch() {
        //only switch when not locked, has turn and no cooldown
        if (!hasTurn() || locked || cooldown > 0) {
            return false;
        }
        ExtendedPieceModel newPiece;
        Player newPieceOwner = null;
        //if the found piece is occupied, only switch is push is true, which makes the previous player switching to the next piece
        boolean push = isExtendeSpecial();
        //the mapping of switch type to range can be altered by input player
        // closest
        switch (range) {
            case 1:
                newPieceOwner = BoardLogic.getClosestPlayer(this);
                break;
            case 2:
                newPieceOwner = BoardLogic.getFurthestPlayer(this);
                break;
            case 3:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.pawn, side));
                break;
            case 4:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.knight, side));
                break;
            case 5:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.bischop, side));
                break;
            case 6:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.rook, side));
                break;
            case 7:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.king, side));
                break;
            case 8:
                newPieceOwner = MetaConfig.getBoardModel().getPlayerByPiece(MetaConfig.getBoardModel().getPieceByTypeAndSide(ExtendedPieceModel.PieceType.queen, side));
                break;

        }
        if (range == 1) {

        } // furthest
        else if (range == 2) {
            newPieceOwner = BoardLogic.getFurthestPlayer(this);
        } else if (range == 2) {
            newPieceOwner = BoardLogic.getFurthestPlayer(this);
        }
        if (newPieceOwner != null) {
            newPiece = newPieceOwner.getControlledModel();
            //check if player is pushable if not check if push activated
            if (newPiece != null) {
                //switch of controlled model
                newPieceOwner.setControlledModel(controlledModel);
                setControlledModel(newPiece);
                //set cooldown and lock
                payForSwitch();
                return true;
            }
        }
        return false;
    }

    //check if turn changed, decrease or increase cooldown
    //if the turn calculated from current is different from turn calculated from the abs time from the latest registered turnchange
    protected boolean turnChanged() {

        ExtendedTileModel tile = controlledModel.getTilePosition();
        return MetaClock.getTurn(this) != MetaClock
                .getTurn(tile.getAbsFraction(), getSide(), getAbsTime());

    }
//no decision is made here
    //in player subtypes decide is overriden and used to decide whether a special or movement is made or not e.g. based on input in input player

    public void decide() {
        if (turnChanged()) {
            turnChange();
        }
    }

    public boolean playerIsOverMaxFraction() {
        return controlledModel.getTilePosition().getAbsFraction() >= MetaClock.getMaxFraction();
    }

    public boolean hasTurn() {
        return MetaClock.getTurn(this);
    }

    protected boolean handleMovement(Direction direction) {
        //don't lock for movement once passed max fraction
        if ((hasTurn() && !locked) || playerIsOverMaxFraction()) {
            controlledModel.handleMovement(direction, range, extendedSpecial);
            locked = true;
            return true;
        }
        return false;
    }

    private void payForSwitch() {
        //pay cooldown 
        cooldown = range;
        if (extendedSpecial) {
            cooldown += 3;
        }
        //lock
        locked = true;
        //save current fraction for later 
        absFractionOfActiveDecision = getFraction();
    }

    private int calculateCost() {
        if (extendeRangeOfActiveDecision) {
            return rangeOfActiveDecision + 3;
        }
        return rangeOfActiveDecision;
    }

    private int getFraction() {
        return controlledModel.getTilePosition().getAbsFraction();
    }

    public boolean handleSpecial(boolean on) {

        //if special decision check if no cooldown left 
        if (hasTurn() && cooldown == 0 && on) {
            //save cost of decision
            rangeOfActiveDecision = range;
            if (extendedSpecial) {
                extendeRangeOfActiveDecision = true;
            }
            //save fraction of position on decision
            absFractionOfActiveDecision = getFraction();
            //raise cooldown
            cooldown = calculateCost();
            //execute on piece
            controlledModel.setSpecial(true, range, extendedSpecial);
            return true;
        }
        if (!on) {
            rangeOfActiveDecision = 0;
            extendeRangeOfActiveDecision = false;
            //execute on piece
            controlledModel.setSpecial(false, range, extendedSpecial);
            return true;
        }
        return false;
    }

    public int getRange() {
        return range;
    }

    //get total cooldown relative to fraction of position tile
    public int getRelativeCooldown() {
        if (absFractionOfActiveDecision == 0 || cooldown == 0) {
            return 0;
        }
        return cooldown / portionOfFraction(previousFraction);
    }

    public int getCooldown() {
        return cooldown;
    }

    //returns min 1, the higher the fraction the lower the portion
    public int portionOfFraction(int fraction) {
        return MetaClock.getMaxFraction() / Math.min(fraction, MetaClock.getMaxFraction());
    }

    public int getRangeOfActiveDecision() {
        return rangeOfActiveDecision;
    }

    public boolean isExtendeRangeOfActiveDecision() {
        return extendeRangeOfActiveDecision;
    }

    // on the turn change, you should add cooldown if active decision
    // cooldown is paid after every turn, with the highest range in that turn
    // cooldown is payed after making decision, and while it stays active after
    // every turn change
    public void turnChange() {
        int newFraction = getFraction();
        if (previousFraction >= newFraction && rangeOfActiveDecision == 0 && cooldown > 0) {
            cooldown -= portionOfFraction(newFraction);
            //cooldown is min 0
            cooldown = Math.max(0, cooldown);
        }

        //if a decision is active 
        if (rangeOfActiveDecision > 0) {
            //raise cooldown
            cooldown += calculateCost() * portionOfFraction(absFractionOfActiveDecision);

        }
        // unlock player
        if (hasTurn()) {
            locked = false;
        }
        absTime = MetaClock.getAbsoluteTime();
        previousFraction = newFraction;
    }

    public String getCore() {
        return core;
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
