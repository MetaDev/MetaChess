package model;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedKnightModel extends ExtendedPieceModel {
	public ExtendedKnightModel( int side) {
		super(PieceType.KNIGHT, side, 2);
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public void setDragon() {
		hoover=true;
                killOnHoover=true;
	}
	@Override
	public int[][] getGrid() {
		if (isDragon()) {
			return MetaConfig.getIcon("DRAGON");
		}else{
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

}
