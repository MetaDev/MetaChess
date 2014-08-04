package model;

import meta.MetaConfig;
import meta.MetaConfig.PieceType;

public class ExtendedBishopModel extends ExtendedPieceModel {
	private int rogue = 0;

	public ExtendedBishopModel(int side) {
		super(PieceType.BISHOP, side, 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRogue() {
		return rogue;
	}
	//use the range for gradually stronger rogue modes

	@Override
	public void setRogue(int decideOrRegret) {
		// go rogue 
		if (!MetaConfig.hasRegret(decideOrRegret)) {
			//pass through pieces
			ignoreOccupationOfTile = true;
			//allow penetration of lower fractioned tiles
			penetrateLowerFraction=true;
			//lethal = false;
		} else {
			ignoreOccupationOfTile = false;
			penetrateLowerFraction=false;
			//lethal = true;
		}
		this.rogue = rogue;
	}


}
