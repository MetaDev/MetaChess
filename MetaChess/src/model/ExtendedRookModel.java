package model;

import meta.MetaConfig.PieceType;

public class ExtendedRookModel extends ExtendedPieceModel{
	public ExtendedRookModel(int side) {
		super(PieceType.ROOK, side, 4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setViewing(int viewing) {
		this.viewing = viewing;
	}
}
