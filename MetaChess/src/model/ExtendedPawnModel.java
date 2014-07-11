package model;

import meta.MetaConfig.PieceType;

public class ExtendedPawnModel extends ExtendedPieceModel {
	private int turn = 0;
	private boolean bound=false;
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
	public int getRange() {
		return 1;
	}

	@Override
	public int getMaxRange() {
		return 1 + influencedMaxRange;
	}

	@Override
	public void setTurn(int test) {
		this.turn = (this.turn + 2) % 8;
	}
	@Override
	public int getTurn(){
		return turn;
	}

	@Override
	public int getNrOfViewTiles() {
		if (getTilePosition().getParent().getLevel() == 0) {
			return 4;
		}
		return 4 + influencedTileView;
	}
	

}
