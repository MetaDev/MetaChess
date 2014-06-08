package model.paramobjects;

import meta.MetaMapping;

public class POMovement implements ParamObject{

	@Override
	public int getParam() {
		return MetaMapping.getBoardModel().getPlayer().getControlledModel().getMovementRange();
	}

	@Override
	public void setParam(int param) {
		 MetaMapping.getBoardModel().getPlayer().getControlledModel().setRange(param);;
	}

}
