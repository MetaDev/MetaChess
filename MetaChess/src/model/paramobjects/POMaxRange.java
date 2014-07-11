package model.paramobjects;

import meta.MetaConfig;

public class POMaxRange implements ParamObject{

	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getPlayer().getControlledModel().getMaxRange();
	}

	@Override
	public void setParam(int param) {
		 MetaConfig.getBoardModel().getPlayer().getControlledModel().setMaxRange(param);
	}

}
