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
		//only change if the key is pressed
			MetaConfig.getBoardModel().getPlayer().getControlledModel()
					.setTurn(param);
	}

}