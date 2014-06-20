package model.paramobjects;

import meta.MetaConfig;

public class POLives implements ParamObject{

	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getSideLives(MetaConfig.getBoardModel().getPlayer().getSide());
	}

	@Override
	public void setParam(int param) {
		MetaConfig.getBoardModel().decreaseSideLives(MetaConfig.getBoardModel().getPlayer().getSide(),param);
	}

}
