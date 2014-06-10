package model.paramobjects;

import meta.MetaConfig;

public class PORange implements ParamObject{

	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getPlayer().getControlledModel().getRange();
	}

	@Override
	public void setParam(int param) {
		 //do nothing
	}

}
