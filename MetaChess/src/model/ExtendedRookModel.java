package model;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import meta.MetaConfig.PieceType;

public class ExtendedRookModel extends ExtendedPieceModel{
	public ExtendedRookModel(int side) {
		super(PieceType.ROOK, side, 4);
		// TODO Auto-generated constructor stub
	}
	
	
        //Keep a set of decisionmodels with all allowed decisions for this piece
    private static SortedSet<DecisionModel> allowedDecisions = new TreeSet<>();

        public static void addAllowedDecision(DecisionModel decision) {
                ExtendedRookModel.allowedDecisions.add(decision);
        }
        @Override
        public Set<DecisionModel> getAllowedDecision(){
            return ExtendedRookModel.allowedDecisions;
        }
}
