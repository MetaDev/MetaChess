package model;

import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedKnightModel extends ExtendedPieceModel {
	public ExtendedKnightModel( int side) {
		super(PieceType.KNIGHT, side, 2);
		// TODO Auto-generated constructor stub
	}

	private int dragon=0;
	@Override
	public int isDragon() {
		return dragon;
	}
	@Override
	public void setDragon(int decideOrRegret) {
		this.dragon = decideOrRegret;
		if (!MetaConfig.hasRegret(decideOrRegret)) {
			//if a dragon the piece can step over a tile and kill pieces in the fractioned tile in the proces
			hoover=true;
		}else{
			hoover=false;
		}
	}
	@Override
	public int[][] getGrid() {
		if (!MetaConfig.hasRegret(dragon)) {
			return MetaConfig.getIcon("DRAGON");
		}else{
			return super.getGrid();
		}
	}
}
