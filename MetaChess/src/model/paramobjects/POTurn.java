package model.paramobjects;

import meta.MetaConfig;

public class POTurn implements ParamObject {
	// return turn relative to parent
	@Override
	public int getParam() {
		return MetaConfig.getBoardModel().getPlayer().getControlledModel()
				.getTurn();
	}

	@Override
	public void setParam(int param) {
		if (param != 0)
			MetaConfig.getBoardModel().getPlayer().getControlledModel()
					.setTurn(param);
	}

}