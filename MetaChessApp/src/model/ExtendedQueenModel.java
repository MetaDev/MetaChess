package model;

import java.util.HashSet;
import java.util.Set;
import meta.MetaConfig.PieceType;

public class ExtendedQueenModel extends ExtendedPieceModel {
//Keep a set of decisionmodels with all allowed decisions for this piece

    private static Set<DecisionModel> allowedDecisions = new HashSet<>();

    public static void addAllowedDecision(DecisionModel decision) {
        ExtendedQueenModel.allowedDecisions.add(decision);
    }

    @Override
    public Set<DecisionModel> getAllowedDecision() {
        return ExtendedQueenModel.allowedDecisions;
    }

    public ExtendedQueenModel(int side) {
        super(PieceType.QUEEN, side, 8);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void setSpecial(boolean yes, int range, boolean extendedSpecial) {
        //do nothing
    }

}
