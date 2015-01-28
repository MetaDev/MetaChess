package model;

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

	@Override
	public int getMovementRange() {
		return 1;
	}
//Keep a set of decisionmodels with all allowed decisions for this piece
    private static SortedSet<DecisionModel> allowedDecisions = new TreeSet<>();

        public static void addAllowedDecision(DecisionModel decision) {
                ExtendedPawnModel.allowedDecisions.add(decision);
        }
        @Override
        public Set<DecisionModel> getAllowedDecision(){
            return ExtendedPawnModel.allowedDecisions;
        }



}
