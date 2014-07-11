package model.paramobjects;

import meta.MetaConfig;

public class PODragon implements ParamObject{

	@Override
	public int getParam() {
		return  MetaConfig.getBoardModel().getPlayer().getControlledModel().isDragon();
	}

	@Override
	public void setParam(int param) {
		 MetaConfig.getBoardModel().getPlayer().getControlledModel().setDragon(param);
	}

}
