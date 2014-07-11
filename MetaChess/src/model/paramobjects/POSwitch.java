package model.paramobjects;

import meta.MetaConfig;

public class POSwitch implements ParamObject{

	@Override
	public int getParam() {
		//doesn't do anything, the player can quite clearly see which piece he's playing with
		return 0;
	}

	@Override
	public void setParam(int param) {
		MetaConfig.getBoardModel().getPlayer().switchPiece(param);
	}

}
