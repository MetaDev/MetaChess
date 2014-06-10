package model.paramobjects;

import meta.MetaConfig;

public class POLives implements ParamObject{

	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getTeamLives(MetaConfig.getBoardModel().getPlayer().getSide());
	}

	@Override
	public void setParam(int param) {
		MetaConfig.getBoardModel().setTeamLives(MetaConfig.getBoardModel().getPlayer().getSide(),param);
	}

}
