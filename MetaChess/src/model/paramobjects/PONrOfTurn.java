package model.paramobjects;

import meta.MetaClock;

public class PONrOfTurn implements ParamObject{
	//return turn relative to parent
	@Override
	public int getParam() {
		return MetaClock.getRelativeTileTurn();
	}

	@Override
	public void setParam(int param) {
		 //can't be set
	}

}
