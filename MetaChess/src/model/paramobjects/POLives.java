package model.paramobjects;

import meta.MetaMapping;

public class POLives implements ParamObject{

	@Override
	public int getParam() {
		return MetaMapping.getBoardModel().getTeamLives(MetaMapping.getBoardModel().getPlayer().getSide());
	}

	@Override
	public void setParam(int param) {
		MetaMapping.getBoardModel().setTeamLives(MetaMapping.getBoardModel().getPlayer().getSide(),param);
	}

}
