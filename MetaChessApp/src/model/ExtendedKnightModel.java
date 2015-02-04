package model;

import engine.Directions;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import logic.PieceMovementLogic;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedKnightModel extends ExtendedPieceModel {

    public ExtendedKnightModel(int side) {
        super(PieceType.KNIGHT, side, 2);
        // TODO Auto-generated constructor stub
    }

    
    @Override
    public int[][] getGrid() {
        if (isDragon()) {
            return MetaConfig.getIcon("DRAGON");
        } else {
            return super.getGrid();
        }
    }

    //Keep a set of decisionmodels with all allowed decisions for this piece
    private static SortedSet<DecisionModel> allowedDecisions = new TreeSet<>();

    public static void addAllowedDecision(DecisionModel decision) {
        ExtendedKnightModel.allowedDecisions.add(decision);
    }

    @Override
    public Set<DecisionModel> getAllowedDecision() {
        return ExtendedKnightModel.allowedDecisions;
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        if (yes) {
            killOnHooverTiles=range;
            if (extendedSpecial) {
                //set negative
                killOnHooverTiles*=-1;
            }
        } //reset
        else {
            killOnHooverTiles = 0;
        }
    }
    
    @Override
     public void step(Directions.Direction direction, int range, boolean extendedSpecial) {
       super.step(direction, 1, extendedSpecial);
    }

}
