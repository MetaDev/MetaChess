package model.paramobjects;

import meta.MetaConfig;

public class POHorizon implements ParamObject{


	@Override
	public int getParam() {
		return  MetaConfig.getBoardModel().getPlayer().getControlledModel().getViewing();
	}

	@Override
	public void setParam(int param) {
		 MetaConfig.getBoardModel().getPlayer().getControlledModel().setViewing(param);
	}


}
