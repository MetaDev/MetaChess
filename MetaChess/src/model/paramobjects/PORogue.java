package model.paramobjects;

import meta.MetaConfig;

public class PORogue implements ParamObject{

	@Override
	public int getParam() {
		return  MetaConfig.getBoardModel().getPlayer().getControlledModel().getRogue();
	}

	@Override
	public void setParam(int param) {
		 MetaConfig.getBoardModel().getPlayer().getControlledModel().setRogue(param);
	}


}
