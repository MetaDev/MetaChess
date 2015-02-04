package model;

import engine.Directions;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedPawnModel extends ExtendedPieceModel {

    private boolean bound = false;

    public boolean isBound() {
        return bound;
    }

    public void setBound(boolean bound) {
        this.bound = bound;
    }

    public ExtendedPawnModel(int side) {
        super(PieceType.PAWN, side, 1);
    }

  
//Keep a set of decisionmodels with all allowed decisions for this piece
    private static SortedSet<DecisionModel> allowedDecisions = new TreeSet<>();

    public static void addAllowedDecision(DecisionModel decision) {
        ExtendedPawnModel.allowedDecisions.add(decision);
    }

    @Override
    public Set<DecisionModel> getAllowedDecision() {
        return ExtendedPawnModel.allowedDecisions;
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        //when extended special is used, one can turn with a higher precision
        if (yes) {
            if (extendedSpecial) {
                this.turn = (this.turn + 1) * range % 8;

            } else {
                this.turn = (this.turn + 2) * range % 8;

            }
        }
        //no else because it's an cyclic variabel, so it doesn't return to default variabel on undecision
    }
    
     @Override
     public void step(Directions.Direction direction, int range, boolean extendedSpecial) {
       //use turn to alter direction
       super.step(Directions.turnDirection(direction, turn), 1, extendedSpecial);
    }

}
